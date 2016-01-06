package me.r2d2.batch;

import me.r2d2.restaurant.Restaurant;
import me.r2d2.restaurant.RestaurantRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by Park Ji Hong, ggikko.
 */

@Service
public class BlogDataProcessing {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private static boolean flagForEexistWordInCsv = false;

    private static String REQUEST_URL = "http://openapi.naver.com/search";
    private static String API_KEY = "?key=c3fd9241f5bb48df957e1aa26f933a44";
    private static String QUERY = "&query=";
    private static String KEYWORD = "강남+회사+점심";
    private static String DISPLAY = "&display=";
    private static int DISPLAY_NUM = 100;
    private static String START = "&start=";
    private static int START_NUM = 1;
    private static String TARGET = "&target=blog";
    private static String SORT = "&sort=sim";

    public void getDataAndDataProcessing(String keyword) throws IOException {

        /** 검색해서 가져올 키워드 지정 */
        KEYWORD = keyword;

        /**음식점 데이터 가져오기 */
        List<String> famousRestaurantNameFromCsvFile = getFamousRestaurantNameFromCsvFile();

        /** 가중치를 위한 리스트 */
        ArrayList<SelectedRestaurant> restaurantses = new ArrayList<>();

        for (int i = 0; i < 1000; i = i + 100) {

            if (i == 0) i = START_NUM;

            /** 블로그 데이터 가져오기 */
            Elements elements = getDataFromSpecificUrl(i);

            /** 블로그 데이터 처리 과정 후에 담을 리스트 */
            List<String> preprocessedList = new ArrayList<String>();

            /** 쪼개진 리스트 */
            List<String> words = new ArrayList<String>();

            for (Element temp : elements) {

                /** 블로그 데이터 */
                String setenceFromBlog = temp.text();

                /**  블로그 데이터 단어로 쪼개기 */
                words = splitWords(setenceFromBlog);

                /** 블로그 데이터 전처리 과정 */
                preprocessedList = preprocessing(words);

                /** 블로그 데이터와 음식점 데이터 비교(포함관계 인지 아닌지 */
                List<String> comparedData = csvFileCompareWithBlogInfo(famousRestaurantNameFromCsvFile, preprocessedList);


                /** 음식점 나온 순서대로 가중치를 준다 */
                for (String com : comparedData) {

                    boolean b = checkIsSameRestaurantName(restaurantses, com);

                    if (b == false) {
                        SelectedRestaurant newRes = new SelectedRestaurant(com, 0);
                        restaurantses.add(newRes);
                    }

                    if (restaurantses.size() == 0) {
                        SelectedRestaurant initRes = new SelectedRestaurant(com, 0);
                        restaurantses.add(initRes);
                    }
                }

                /** 음식점 이름을 가져온다. */
                String resName = existWordInCsv(setenceFromBlog, comparedData);

                /** 음식점 이름을 제외하고 키워드 리스트를 만든다 */
                List<String> keywords = makeKeywordsList(preprocessedList, resName);

            }


            if (i == START_NUM) i = 0;
        }

        /** 랭킹 10개만 넣을 리스트  */
        List<String> last = new ArrayList<>();

        /** x 만큼 이상이면 */
        for (int m = 6; m > 0; m--) {
            last.clear();
            getListIfCountXUpper(restaurantses, last, m);
            if (last.size() > 10) break;
        }

        /** 랜덤으로 원하는 사이즈 만큼 크기에 맞게 랜덤 숫자를 가져옴 */
        if(last.size()>0) {
            int[] random = getRandom(last, 10);
            /** 랜덤 숫자를 이용하여 데이터 10개 뽑아봄 */
            for (int l : random) {
                String s = last.get(l);
            }

            /** 데이터 삽입 */
            Restaurant restaurant = new Restaurant();
            restaurant.setSubwayNumber("강남역");
            restaurant.setRestaurant1(last.get(random[0]));
            restaurant.setRestaurant2(last.get(random[1]));
            restaurant.setRestaurant3(last.get(random[2]));
            restaurant.setRestaurant4(last.get(random[3]));
            restaurant.setRestaurant5(last.get(random[4]));
            restaurant.setRestaurant6(last.get(random[5]));
            restaurant.setRestaurant7(last.get(random[6]));
            restaurant.setRestaurant8(last.get(random[7]));
            restaurant.setRestaurant9(last.get(random[8]));
            restaurant.setRestaurant10(last.get(random[9]));
            restaurantRepository.save(restaurant);
        }
    }

    /**
     * 음식점 주소와 정보가 들어가 있는 csv파일 정보를 읽어들여 음식점 정보를 메모리 상에 올려 놓는다
     */
    public List<String> getFamousRestaurantNameFromCsvFile() throws IOException {

        /** File Reader */
        FileReader fileReader = null;

        /** CSV Parser */
        CSVParser csvParser = null;

        //CSV파일에 맵핑될 Header를 정의해준다.
        final String[] FILE_HEADER_MAPPING = {"연번", "업소명", "지번"};

        /** CSVFormat */
        CSVFormat csvFormat = CSVFormat.newFormat(',').withQuote('"').withHeader(FILE_HEADER_MAPPING);

        /** CSV파일을 읽어온다 */

        /** 서버에서 구동시 절대 좌표로 파일을 불러들임 */
//        Resource resource = resourceLoader.getResource("gangnam.csv");
//        File file = resource.getFile();
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

//        fileReader = new FileReader(file);

//        String path = "/root/gangnam.csv";
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
//        FileInputStream fileInputStream = new FileInputStream(path);
//        File file = new File(path);

        fileReader = new FileReader(new File(getClass().getClassLoader().getResource("gangnam.csv").getFile()));

//        fileReader = new FileReader(file);
        /** CSV파일 파서에 파일 헤더 맵핑과 구분자 포맷을 설정해준다 */
        csvParser = new CSVParser(fileReader, csvFormat);
//        csvParser = new CSVParser(inputStreamReader, csvFormat);
//        csvParser = new CSVParser(in, csvFormat);
//        csvParser = new CSVParser(fileReader, csvFormat);

        /** 전체 음식점 정보 이름 리스트 */
        List<String> entireRestaurantName = new ArrayList<>();

        /** 모든 줄의 레코드를 리스트에 담는다 */
        for (CSVRecord record : csvParser) {
            String tempRestaurantName = record.get("업소명").trim().replaceAll(" ", "").replaceAll("\\s", "");
            entireRestaurantName.add(tempRestaurantName);
        }

        /** 해당 리스트 반환 */
        return entireRestaurantName;
    }

    /**
     * 키워드로 검색된 네이버 블로그에서 정보 가져옴
     */
    public static Elements getDataFromSpecificUrl(int i) throws IOException {

        String requestURL = REQUEST_URL + API_KEY + QUERY + KEYWORD + DISPLAY + DISPLAY_NUM + START + String.valueOf(i) + TARGET + SORT;

        /** URL 로 요청 HTML 페이지 가져온다 */
        Document document = Jsoup.parse(new URL(requestURL).openStream(), "UTF8", requestURL);
//                .connect(requestURL).get();

        /** XML 태그에 맞게 타이틀만 가져온다 */
        return document.select("rss").select("channel").select("item").select("title");
    }

    /**
     * 단어들 -> 단어 쪼개는 작업
     */
    public static List<String> splitWords(String sentence) {
        StringTokenizer st = new StringTokenizer(sentence, " ");
        List<String> tempList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            tempList.add(st.nextToken());
        }
        return tempList;
    }

    /**
     * 단어 전처리 과정 필요없는 문구 특수문자 + 키워드 삭제
     */
    public static List<String> preprocessing(List<String> inputWords) {

        //TODO : List화 하여 필터링 해야함
        List<String> collect = inputWords
                .parallelStream()
                .map(s -> s.replaceAll("[-+.^:,/<>~();!―《♬☞♪#'@‘’♥*♡&?%_》☆★ ]", ""))
                .map(s -> s.replaceAll("\\[", ""))
                .map(s -> s.replaceAll("\\]", ""))
                .map(s -> s.replaceAll("\"", ""))
                .map(s -> s.replaceAll(" ", ""))
                .map(s -> s.replaceAll("b", ""))
                .map(s -> s.replaceAll("lt", ""))
                .map(s -> s.replaceAll("gt", ""))
                .map(s -> s.replaceAll("quot", ""))
                .map(s -> s.replaceAll("ㅎ", ""))
                .map(s -> s.replaceAll("ㅋ", ""))
                .map(s -> s.trim())
                .filter(s -> !(s.length() == 1))
                .filter(s -> !s.isEmpty())
                .filter(s -> !s.contains("강남"))
                .filter(s -> !s.contains("점심"))
                .filter(s -> !s.contains("회사"))
                .collect(toList());

        return collect;
    }

    //블로그에서 가져온 리스트가 음식점 csv파일에 존재하는지 검사하고 있으면 이를 List로 반환한다
    public static List<String> csvFileCompareWithBlogInfo(List<String> csvFileList, List<String> blogDataList) {

        List<String> blogDataListFiltered = filterUselessData(blogDataList);

        List<String> tempList = new ArrayList<>();

        for (String blogWord : blogDataListFiltered) {

            /** csv에 포함한 단어와 blog데이터 단어를 비교하여 포함하고 있으면 리스트에 더한다 */
            for (String csvWord : csvFileList) {

                if (csvWord.contains(blogWord)) {
                    tempList.add(csvWord);
                }
            }
        }
        return tempList;
    }

    /**
     * 필요 없는 데이터를 필터링 한다
     */
    private static List<String> filterUselessData(List<String> blogDataList) {

        //TODO : List화 하여 필터링 해야함 - refactoring 시급
        List<String> collect = blogDataList.parallelStream().filter(s -> !s.contains("맛집"))
        .filter(s -> !s.contains("파스타")).filter(s -> !s.contains("카페"))
        .filter(s -> !s.contains("맛집")).filter(s -> !s.contains("피자")).filter(s -> !s.contains("햄버거"))
        .filter(s -> !s.contains("치킨")).filter(s -> !s.contains("강남")).filter(s -> !s.contains("순대국"))
        .filter(s -> !s.contains("맥주")).filter(s -> !s.contains("쌈밥")).filter(s -> !s.contains("맛있는"))
        .filter(s -> !s.contains("도시락")).filter(s -> !s.contains("줗은")).filter(s -> !s.contains("있는"))
        .filter(s -> !s.contains("역삼동")).filter(s -> !s.contains("최고")).filter(s -> !s.contains("식당"))
        .filter(s -> !s.contains("맛나는")).filter(s -> !s.contains("냉면")).filter(s -> !s.contains("먹고"))
        .filter(s -> !s.contains("전문점")).filter(s -> !s.contains("청국장")).filter(s -> !s.contains("감자탕"))
        .filter(s -> !s.contains("술집")).filter(s -> !s.contains("김밥")).filter(s -> !s.contains("생고기"))
        .filter(s -> !s.contains("왕돈까스")).filter(s -> !s.contains("역삼")).filter(s -> !s.contains("불고기"))
        .filter(s -> !s.contains("좋은")).filter(s -> !s.contains("돈부리")).filter(s -> !s.contains("한우"))
        .filter(s -> !s.contains("정식")).filter(s -> !s.contains("스타일")).filter(s -> !s.contains("찜닭"))
        .filter(s -> !s.contains("나무")).filter(s -> !s.contains("집밥")).filter(s -> !s.contains("서울"))
        .filter(s -> !s.contains("쌀국수")).filter(s -> !s.contains("낙지")).filter(s -> !s.contains("삼성"))
        .filter(s -> !s.contains("나는")).filter(s -> !s.contains("칼국수")).filter(s -> !s.contains("흙돼지"))
        .filter(s -> !s.contains("샐러드")).filter(s -> !s.contains("카레")).filter(s -> !s.contains("오늘"))
        .filter(s -> !s.contains("돈까스")).filter(s -> !s.contains("샤브샤브")).filter(s -> !s.contains("커피"))
        .filter(s -> !s.contains("시원한")).filter(s -> !s.contains("초밥")).filter(s -> !s.contains("밥상"))
        .filter(s -> !s.contains("비빔밥")).filter(s -> !s.contains("행복")).filter(s -> !s.contains("화요리"))
        .filter(s -> !s.contains("족발")).filter(s -> !s.contains("코스")).filter(s -> !s.contains("화로구이"))
        .filter(s -> !s.contains("쭈꾸미")).filter(s -> !s.contains("김치찌개")).filter(s -> !s.contains("해물"))
        .filter(s -> !s.contains("으로")).filter(s -> !s.contains("불백")).filter(s -> !s.contains("사람"))
        .filter(s -> !s.contains("매운")).filter(s -> !s.contains("뚝배기")).filter(s -> !s.contains("스파게티"))
        .filter(s -> !s.contains("즉석떡볶이")).filter(s -> !s.contains("제주")).filter(s -> !s.contains("대치동"))
        .filter(s -> !s.contains("최강")).filter(s -> !s.contains("부대찌개")).filter(s -> !s.contains("2호점"))
        .filter(s -> !s.contains("백반")).filter(s -> !s.contains("분식집")).filter(s -> !s.contains("한식"))
        .filter(s -> !s.contains("짬뽕")).filter(s -> !s.contains("해피")).filter(s -> !s.contains("전골"))
        .collect(toList());

        return collect;
    }

    /**
     * 같은 이름의 음식점이 있는지 확인하는 메소드
     */
    private static boolean checkIsSameRestaurantName(ArrayList<SelectedRestaurant> restaurantses, String com) {
        for (int v = 0; v < restaurantses.size(); v++) {

            SelectedRestaurant restaurantOne = restaurantses.get(v);
            if (restaurantOne.getRestaurantName().equals(com)) {
                int count1 = restaurantOne.getCount();
                restaurantOne.setCount(++count1);
                restaurantses.set(v, restaurantOne);
                return true;
            }

        }
        return false;
    }

    /**
     * CSV파일 안에 있는 단어들과 문장안에 단어들을 비교하여 음식점 이름을 찾아낸다
     */
    public static <T> String existWordInCsv(String sentenceFromBlog, List<T> comparedData) {
        List<String> words = splitWords(sentenceFromBlog);
        for (T resName : comparedData) {
            for (String word : words) {
                if (word.contains(String.valueOf(resName))) {
                    flagForEexistWordInCsv = true;
                    return word;
                }
            }
        }
        return null;
    }

    /**
     * 문장에서 음식점 이름을 포함한 문장을 제외한 모든 단어들을 리스트로 만든다
     */
    public static List<String> makeKeywordsList(List<String> words, String resName) {

        List<String> tempList = new ArrayList<>();
        for (String word : words) {
            if (resName != null) {
                if (!resName.contains(word)) {
                    if (word != null) {
                        tempList.add(word);
                    }
                }
            }
        }
        return tempList;
    }

    /**
     * 지정된 카운트 이상인 음식점만 반환한다
     */
    private static void getListIfCountXUpper(ArrayList<SelectedRestaurant> restaurantses, List<String> last, int i) {
        for (SelectedRestaurant selectedRestaurant : restaurantses) {
            if (selectedRestaurant.getCount() > i) {
                last.add(selectedRestaurant.getRestaurantName());
            }
        }
    }

    /**
     * 원하는 리스트와 사이즈를 넣으면 랜덤으로 수를 뽑아낸다.
     */
    private int[] getRandom(List list, int lastSize) {

        /** 리스트의 사이즈 개수 */
        int size = list.size();

        /** 인트형 배열 선언 */
        int a[] = new int[lastSize];

        /** 랜덤 객체 생성 */
        Random random = new Random();

        /** 원하는 개수 만큼 뽑기 위한 for문 */
        for (int i = 0; i < lastSize; i++) {
            /** 0 ~ 원하는 크기 만큼의 숫자를 뽑음 */
            a[i] = random.nextInt(size);

            /** 중복제거를 위한 for문 */
            for (int j = 0; j < i; j++) {

                /** 현재 a[]에 저장된 랜덤숫자와 이전에 a[]에 들어간 숫자 비교 */
                if (a[i] == a[j]) {
                    i--;
                }
            }
        }
        return a;
    }
}
