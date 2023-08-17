package org.example.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.httpserver.utils.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;

    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){

    }

    private static synchronized ConfigurationManager getSyncInstance(){
        if (myConfigurationManager==null){
                    myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;

    }

    public static ConfigurationManager getInstance(){
        if (myConfigurationManager==null){
            return getSyncInstance();
        }
        return myConfigurationManager;
    }

    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader= null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuffer stringBuffer=new StringBuffer();
        int i;
        while (true){
            try {
                if (!(( i=fileReader.read())!=-1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            stringBuffer.append((char) i);
        }
        JsonNode conf= null;
        try {
            conf = Json.parse(stringBuffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing config file",e);
        }
        try {
            myCurrentConfiguration=Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing config file down",e);
        }
    }
    public Configuration getCurrentConfiguration(){
if (myCurrentConfiguration==null){
    throw new HttpConfigurationException("No current configuration set");
}
return myCurrentConfiguration;
    }
}
