package ru.vkarpov.dev;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
Читаем и пишем в файл: Human
*/

public class Solution {
    public static void main(String[] args) {
        try {
            File your_file_name = File.createTempFile("/Users/Vlad/Downloads/@Learning/db.txt", null);
            OutputStream outputStream = new FileOutputStream(your_file_name);
            InputStream inputStream = new FileInputStream(your_file_name);

            Human ivanov = new Human("Ivanov", new Asset("home", 999_999.99), new Asset("car", 2999.99));
            ivanov.save(outputStream);
            outputStream.flush();

            Human somePerson = new Human();
            somePerson.load(inputStream);
            inputStream.close();

            if (ivanov.equals(somePerson)) {
                System.out.println("Обьекты равны!");
            } else {
                System.out.println("Обьекты не равны!");
            }

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Oops, something wrong with my file");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops, something wrong with save/load method");
        }
    }

    public static class Human {
        public String name;
        public List<Asset> assets = new ArrayList<>();

        public Human() {
        }

        public Human(String name, Asset... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Human human = (Human) o;

            if (name != null ? !name.equals(human.name) : human.name != null) return false;
            return assets != null ? assets.equals(human.assets) : human.assets == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (assets != null ? assets.hashCode() : 0);
            return result;
        }

        public void save(OutputStream outputStream) throws Exception {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(name + ",");

            for (Asset as : assets){
                stringBuilder.append(as.getName() + ":" + as.getPrice() + ";");
            }
            stringBuilder.append("\n");
            outputStream.write(String.valueOf(stringBuilder).getBytes(StandardCharsets.UTF_8));
        }

        public void load(InputStream inputStream) throws Exception {
            ArrayList<String> list = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.ready()){
                list.add(reader.readLine());
            }
            for (String str : list){
                String[] array = str.split(",");
                name = array[0];
                String[] array2 = array[1].split(";");
                for (int i = 0; i < array2.length; i++) {
                    String[] array3 = array2[i].split(":");
                    assets.add(new Asset(array3[0], Double.parseDouble(array3[1])));
                }
            }
        }

    }

}

