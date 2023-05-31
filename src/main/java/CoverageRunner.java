import java.util.*;

public class CoverageRunner {

    public static Map<List<Integer>, Integer> generateMBUU(List<List<Integer>> numbers){
        Map<List<Integer>, Integer> numberMap=Utils.convertListToMap(numbers) ;
        Map<List<Integer>, Integer> cases= new HashMap();

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
                            if (!cases.containsKey(key))
                                cases.put(key, result);

                        }else {

                            break;
                        }
                    }
                }
            }
        }

        return cases;

    }


    public static Map<List<Integer>, List<String>> generateTestMCDC(int numberCond, List<List<Integer>> numbers) {
        Map<List<Integer>, Integer> numberMap = Utils.convertListToMap(numbers);
        int length = (int) numberMap.keySet().stream().count();
        Map<List<Integer>, List<String>> cases = new HashMap();


        String condition = "";
        int limit= (int) (Math.pow(2, numberCond)/2);

        for (int i = 0; i < numberCond; i++) {
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
                        List<String> existingValueListForKey = cases.get(key);
                        List<String> existingValueListForTmp = cases.get(tmp);

                        if (existingValueListForKey != null) {
                            existingValueListForKey.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            cases.put(key, newValueList);
                        }

                        if (existingValueListForTmp != null) {
                            existingValueListForTmp.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            cases.put(tmp, newValueList);
                        }
                        tmpNumbers.remove(key);
                        tmpNumbers.remove(tmp);
                        break;
                    }

                }

            }
        }


        return cases;
    }

    public static List<List<Integer>> findTestCasesForMcDc(Map<List<Integer>, List<String>> cases){
        List<Integer> keyWithLongestValue = null;
        int maxLength = 0;
        List<Integer> numConditions=new ArrayList<>();
        if (!cases.isEmpty()) {
            numConditions = cases.keySet().iterator().next();
        }



        List<List<Integer>> testCases= new ArrayList<>();
        Map<List<Integer>, List<String>> tmpCases= new HashMap<>(cases);

        for (Map.Entry<List<Integer>, List<String>> entry : tmpCases.entrySet()) {
            List<String> value = entry.getValue();
            int length = value.size();

            if (length > maxLength) {
                maxLength = length;
                keyWithLongestValue = entry.getKey();
            }

        }




        testCases.add(keyWithLongestValue);
        for (String value : new ArrayList<>(tmpCases.get(keyWithLongestValue))) {
            for (List<Integer> val : new ArrayList<>(tmpCases.keySet())) {
                List<String> KeValues = tmpCases.get(val);
                if (KeValues.contains(value)) {
                    if(!(testCases.contains(val))){
                        testCases.add(val);
                        tmpCases.remove(val);
                    }
                }
            }
        }

        char missingLetter='\0';
        List<String> value = cases.get(keyWithLongestValue); // Wert des Schlüssels erhalten
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
                    if(!(testCases.contains(val))){
                        testCases.add(val);
                        tmpCases.remove(val);
                    }
                }

            }


        }

        return testCases;

    }


    public static List<List<Integer>> findConditionsForMcDC(List<List<Integer>> testCases, List<List<Integer>> numbers){
        List<List<Integer>> result= new ArrayList<>(testCases);
        List<List<Integer>> cases= new ArrayList<>();
        Map<List<Integer>, Integer> numberMap= Utils.convertListToMap(numbers);
        for (List<Integer> resultList: result) {
            if(numberMap.containsKey(resultList)){
                int value= numberMap.get(resultList);
                List<Integer> tmp = new ArrayList<>(resultList);
                tmp.add(value);
                cases.add(tmp);


            }
        }
        return cases;

    }



    public static void generateList(List<String> pathNames, boolean coverage) {
        int counter= 0;
        String basePathToWrite="/Users/yaseminakaydin/Desktop/exercises/Results/";
        for (String path: pathNames) {
            if(coverage){
                String[] elements= Parser.readData(path);
                String fileName= Utils.extractFileName(path);
                Parser.writeData(basePathToWrite + fileName + "McDcTest.md" ,
                        Parser.header(elements),findConditionsForMcDC(
                                findTestCasesForMcDc(
                                        generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements)) );

                counter++;

            } else {
                String[] elements= Parser.readData(path);
                String fileName= Utils.extractFileName(path);
                List<List<Integer>> nums= Utils.mapToList(generateMBUU(Parser.numbers(elements)));
                Parser.writeData(basePathToWrite + fileName + "MBUUTest.md", Parser.header(elements), nums);
            }



        }


    }
}
