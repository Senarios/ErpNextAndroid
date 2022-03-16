package com.example.erpnext.callbacks;


import com.example.erpnext.models.Field;

import java.util.List;

public interface SectionBreaksCallback {
    void onSectionClick(List<Field> fieldList);
}
