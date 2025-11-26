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
package br.edu.ifsp.leo.repair;

import br.edu.ifsp.leo.mechanic.Mechanic;
import br.edu.ifsp.leo.vehicle.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;


@Table(name = "repairs")
@Entity(name = "Repair")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entryDate;
    private String exitDate;
    private Boolean active;

    @Embedded
    private Mechanic mechanic;

    @Embedded
    private Vehicle vehicle;

    public Repair(RepairRegistrationData data) {
        this.entryDate = data.entryDate();
        this.exitDate = data.exitDate();
        this.mechanic = new Mechanic(data.mechanic());
        this.vehicle = new Vehicle(data.vehicle());
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(RepairUpdateData data) {
        if (data.exitDate() != null) {
            this.exitDate = data.exitDate();
        }
        this.mechanic.update(data);
    }
}