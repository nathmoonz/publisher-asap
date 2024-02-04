package com.publisher.publisherasap;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConvertToJSON{
	
	public void lerArquivo() {
		
        String csvFile = "E:\\desafiohack\\input-data.csv";
        String line;

        List<JSONObject> jsonData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split(";");
                JSONObject JSONObj = new JSONObject();
                JSONObj.put("transacaoId", data[0]);
                JSONObj.put("dataTransacao", data[1]);
                JSONObj.put("documento", data[2]);
                JSONObj.put("nome", data[3]);
                JSONObj.put("idade", data[4]);
                JSONObj.put("valor", data[5]);
                JSONObj.put("numParcelas", data[6]);

//                jsonData.add(JSONObj);
//                this.queueSender.send(JSONObj.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        return "";
    }
}