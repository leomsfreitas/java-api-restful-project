/*
 * Copyright (c) 2025 Leo Freitas
 * GitHub: https://github.com/leomsfreitas
 *
 * Licensed under the MIT License.
 * You may obtain a copy of the License at
 * https://opensource.org/licenses/MIT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package br.edu.ifsp.leo.controller;

import br.edu.ifsp.leo.repair.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/repairs")
public class RepairController {

    @Autowired
    private RepairRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<RepairDetailData> register(@RequestBody @Valid RepairRegistrationData data,
                                                     UriComponentsBuilder uriBuilder) {
        var repair = new Repair(data);
        repository.save(repair);

        var uri = uriBuilder.path("/repairs/{id}").buildAndExpand(repair.getId()).toUri();
        return ResponseEntity.created(uri).body(new RepairDetailData(repair));
    }

    @GetMapping
    public ResponseEntity<Page<Repair>> listAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RepairListingData>> listActive() {
        var listing = repository.findAllByActiveTrue().stream()
                .map(RepairListingData::new)
                .toList();

        return ResponseEntity.ok(listing);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairDetailData> detail(@PathVariable Long id) {
        var repair = repository.findById(id);

        if (repair.isPresent()) {
            return ResponseEntity.ok(new RepairDetailData(repair.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RepairDetailData> update(@RequestBody @Valid RepairUpdateData data) {
        var repair = repository.getReferenceById(data.id());
        repair.update(data);

        return ResponseEntity.ok(new RepairDetailData(repair));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity logicalDelete(@PathVariable Long id) {
        var repair = repository.getReferenceById(id);
        repair.deactivate();

        return ResponseEntity.noContent().build();
    }
}