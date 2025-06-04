package com.example.corso.client;


import com.example.corso.request.DocenteRequest;
import com.example.corso.response.DocenteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DocenteService", url = "http://localhost:8080")
public interface DocenteClient {

    @GetMapping("/docenti/{id}")
    public DocenteResponse getDocenteById(@PathVariable("id")Long id);

    @PostMapping("/docenti")
    DocenteResponse saveDocente(@RequestBody DocenteRequest request);

}
