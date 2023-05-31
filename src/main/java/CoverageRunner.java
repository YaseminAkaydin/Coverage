import java.util.*;

public class CoverageRunner {

    static String basePathToWrite="/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/results/";
    static Map<List<Integer>, Integer> numberMap= new HashMap<>();
    static Map<List<Integer>, Integer> casesForMBUU= new HashMap<>();
    static Map<List<Integer>, List<String>> casesForMcDc= new HashMap<>();
    static List<List<Integer>> testCasesMcDc= new ArrayList<>();
    static List<List<Integer>> resultMcDc= new ArrayList<>();
    static List<List<Integer>> resultMBUU= new ArrayList<>();

    public static void generateMBUU(){
        numberMap=Utils.convertListToMap(Parser.numbersList) ;
        casesForMBUU= new HashMap();

        for (List<Integer> key: numberMap.keySet()) {
            int result=numberMap.get(key);
            int result2=0;

            for (int i = 0; i < key.size(); i++) {
                List<Integer> tmp = new ArrayList<>(key);

                if(tmp.get(i)==0){
                    tmp.set(i, 1);
                }else {
                    tmp.set(i, 0);
                }
                for (List<Integer> key2: numberMap.keySet()){
                    if (key2.equals(tmp)){
                        if(numberMap.get(key2)!=null){
                            result2= numberMap.get(key2);
                        }
                        if(!(result==result2)){
                            if (!casesForMBUU.containsKey(key))
                                casesForMBUU.put(key, result);

                        }else {

                            break;
                        }
                    }
                }
            }
        }
        resultMBUU= Utils.mapToList(casesForMBUU);

        //return casesForMBUU;

    }


    public static void generateTestMCDC() {
         numberMap = Utils.convertListToMap(Parser.numbersList);
        int length = (int) numberMap.keySet().stream().count();
        casesForMcDc = new HashMap();


        String condition = "";
        int limit= (int) (Math.pow(2, Parser.numColums)/2);

        for (int i = 0; i < Parser.numColums; i++) {
            Map<List<Integer>, Integer> tmpNumbers = new HashMap(numberMap);


            char letter = (char) ('A' + i);
            condition = String.valueOf(letter);
            for (int j = 1; j <= limit; j++) {
                for (Iterator<Map.Entry<List<Integer>, Integer>> iterator = tmpNumbers.entrySet().iterator(); iterator.hasNext(); ) {

                    Map.Entry<List<Integer>, Integer> entry = iterator.next();

                    List<Integer> key = entry.getKey();
                    int result = entry.getValue();

                    List<Integer> tmp = new ArrayList<>(key);
                    if (tmp.get(i) == 0) {
                        tmp.set(i, 1);
                    } else {
                        tmp.set(i, 0);
                    }
                    int result2 = numberMap.get(tmp);

                    if (result != result2) {
                        List<String> existingValueListForKey = casesForMcDc.get(key);
                        List<String> existingValueListForTmp = casesForMcDc.get(tmp);

                        if (existingValueListForKey != null) {
                            existingValueListForKey.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            casesForMcDc.put(key, newValueList);
                        }

                        if (existingValueListForTmp != null) {
                            existingValueListForTmp.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            casesForMcDc.put(tmp, newValueList);
                        }
                        tmpNumbers.remove(key);
                        tmpNumbers.remove(tmp);
                        break;
                    }

                }

            }
        }


        //return casesForMcDc;
    }

    public static void findTestCasesForMcDc(){
        List<Integer> keyWithLongestValue = null;
        int maxLength = 0;
        List<Integer> numConditions=new ArrayList<>();
        if (!casesForMcDc.isEmpty()) {
            numConditions = casesForMcDc.keySet().iterator().next();
        }


         testCasesMcDc= new ArrayList<>();
        Map<List<Integer>, List<String>> tmpCases= new HashMap<>(casesForMcDc);

        for (Map.Entry<List<Integer>, List<String>> entry : tmpCases.entrySet()) {
            List<String> value = entry.getValue();
            int length = value.size();

            if (length > maxLength) {
                maxLength = length;
                keyWithLongestValue = entry.getKey();
            }

        }




        testCasesMcDc.add(keyWithLongestValue);
        for (String value : new ArrayList<>(tmpCases.get(keyWithLongestValue))) {
            for (List<Integer> val : new ArrayList<>(tmpCases.keySet())) {
                List<String> KeValues = tmpCases.get(val);
                if (KeValues.contains(value)) {
                    if(!(testCasesMcDc.contains(val))){
                        testCasesMcDc.add(val);
                        tmpCases.remove(val);
                    }
                }
            }
        }

        char missingLetter='\0';
        List<String> value = casesForMcDc.get(keyWithLongestValue); // Wert des Schlüssels erhalten
        int length = value.size();
        char limit= (char) ('A'+ length);
        if(length!=numConditions.size()){
            List<Character> letters=new ArrayList<>();
            for (String values: tmpCases.get(keyWithLongestValue)) {
                letters.add(values.charAt(0));
            }

            for (char i = 'A'; i  <= limit; i++) {
                if (!(letters.contains(i))) {
                    missingLetter=i;
                }

            }


            for (List<Integer> val : new ArrayList<>(tmpCases.keySet())){
                List<String> KeValues = tmpCases.get(val);
                if (KeValues.contains(Character.toString(missingLetter)+"1")) {
                    if(!(testCasesMcDc.contains(val))){
                        testCasesMcDc.add(val);
                        tmpCases.remove(val);
                    }
                }

            }


        }

        //return testCasesMcDc;

    }


    public static void findConditionsForMcDC(){
        List<List<Integer>> result= new ArrayList<>(testCasesMcDc);
        resultMcDc= new ArrayList<>();
        numberMap= Utils.convertListToMap(Parser.numbersList);
        for (List<Integer> resultList: result) {
            if(numberMap.containsKey(resultList)){
                int value= numberMap.get(resultList);
                List<Integer> tmp = new ArrayList<>(resultList);
                tmp.add(value);
                resultMcDc.add(tmp);


            }
        }
        //return resultMcDc;

    }

    public static List<List<Integer>> mcDcRunner(){
        generateTestMCDC();
        findTestCasesForMcDc();
        findConditionsForMcDC();
        return resultMcDc;
    }

    public static List<List<Integer>> mbuuRunner(){
        generateMBUU();
        return resultMBUU;
    }



    public static void generateList(List<String> pathNames, boolean coverage) {
        int counter= 0;
        for (String path: pathNames) {
            if(coverage){
                //String[] elements= Parser.readData(path);
                String fileName= Utils.extractFileName(path);
                Parser.writeData(basePathToWrite + fileName + "McDcTest.md" , mcDcRunner());

                counter++;

            } else {
                //String[] elements= Parser.readData(path);
                String fileName= Utils.extractFileName(path);
                Parser.writeData(basePathToWrite+fileName+"MBUUTest.md", mbuuRunner());
                //List<List<Integer>> nums= Utils.mapToList(generateMBUU());
                //Parser.writeData(basePathToWrite + fileName + "MBUUTest.md", nums);
            }



        }


    }

    public static void main(String[] args) {
        Parser.readData("/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/exercises/ex2.md");
        Parser.writeData("/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/results/Test2.md", mcDcRunner());
        Parser.writeData("/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/results/Test3.md",mbuuRunner() );

    }
}
