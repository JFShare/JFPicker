
package com.Jfpicker.wheelpicker.picker_adress.entity;

import androidx.annotation.NonNull;


import java.io.Serializable;
import java.util.Objects;


public class AddressEntity implements Serializable {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddressEntity{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
