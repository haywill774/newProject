package com.example.demoe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class GetCount {
    File file = new File("greetings.txt");
    File newFile = new File("log.txt");
    private final List<String> postedMeeting = new ArrayList<>();
    Map<String, String> mappedString = new HashMap<>();
    private int count = 1;

    @GetMapping("/greeting")
    public String getGreeting() {
        StringBuilder sb = new StringBuilder();
        String x;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            while(( x = bufferedReader.readLine()) != null ){
                sb.append(x);
            }
        } catch(IOException exception){

            System.out.println(exception.getMessage());
        }
        return sb.toString();
    }

    @PostMapping("/greeting")
    public String postGreeting(@RequestBody String message) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(message + "#");

        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return "message successfully saved";
    }

    @GetMapping("/log")
    public String getMessageCount() {
        StringBuilder sb = new StringBuilder();
        String x;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile))){
            while(( x = bufferedReader.readLine()) != null ){
                sb.append(x);
            }
        } catch(IOException exception){

            System.out.println(exception.getMessage());
        }
        return sb.toString();
    }

    @PostMapping("/log")
    public ResponseEntity<String> postMessage(@RequestBody String meeting){

        try(BufferedWriter buff = new BufferedWriter(new FileWriter(newFile,true))){
            buff.write(meeting + "#" + "\n");
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        LocalDate date = LocalDate.now();
        String postedTime = date + " " + count ++;
        mappedString.put(postedTime, meeting);
        postedMeeting.add(meeting);
        System.out.println(postedMeeting);
        return ResponseEntity.ok(String.format("String:\"%s\",was saved in the list",postedMeeting));

    }
}

