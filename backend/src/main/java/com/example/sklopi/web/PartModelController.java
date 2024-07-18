package com.example.sklopi.web;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.service.PartModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partModels")
public class PartModelController {
    @Autowired
    private PartModelService partModelService;

    @GetMapping
    public List<PartModel> getAllPartModels() {
        return partModelService.getAllPartModels();
    }

    @GetMapping("/{id}")
    public PartModel getPartModelById(@PathVariable Long id) {
        return partModelService.getPartModelById(id);
    }

    @PostMapping
    public PartModel createPartModel(@RequestBody PartModel partModel) {
        return partModelService.savePartModel(partModel);
    }

    @PutMapping("/{id}")
    public PartModel updatePartModel(@PathVariable Long id, @RequestBody PartModel partModel) {
//        partModel.setId(id);
        return partModelService.savePartModel(partModel);
    }

    @DeleteMapping("/{id}")
    public void deletePartModel(@PathVariable Long id) {
        partModelService.deletePartModel(id);
    }
}