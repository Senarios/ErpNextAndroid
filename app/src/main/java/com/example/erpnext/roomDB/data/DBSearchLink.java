package com.example.erpnext.roomDB.data;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class DBSearchLink {


    public static void load(Context context, String searchDoctype, String filters, String query, AutoCompleteTextView edit) {
        if (filters != null) {
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query) != null) {
                List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query).getSearchResultList();
                List<String> list = new ArrayList<>();
                for (SearchResult searchResult : searchResults) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                edit.setAdapter(adapter);
                edit.showDropDown();

            } else Notify.Toast("No data found from offline data");
        } else {
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query) != null) {
                List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).getSearchResultList();
                List<String> list = new ArrayList<>();
                for (SearchResult searchResult : searchResults) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                edit.setAdapter(adapter);
                edit.showDropDown();

            } else Notify.Toast("No data found from offline data");
        }
    }

    public static void loadFromRef(Context context, String searchDoctype, String filters, String reference, AutoCompleteTextView edit) {
        if (filters != null) {
            if (MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, filters, reference) != null) {
                List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, filters, reference).getSearchResultList();
                List<String> list = new ArrayList<>();
                for (SearchResult searchResult : searchResults) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                edit.setAdapter(adapter);
                edit.showDropDown();

            } else Notify.Toast("No data found from offline data");
        } else {
            if (MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference) != null) {
                List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).getSearchResultList();
                List<String> list = new ArrayList<>();
                for (SearchResult searchResult : searchResults) {
                    list.add(searchResult.getValue());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                edit.setAdapter(adapter);
                edit.showDropDown();

            } else Notify.Toast("No data found from offline data");
        }
    }

    public static void loadd(Context context, String txt,String searchDoctype, AutoCompleteTextView edit) {
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(txt, searchDoctype) != null) {
            List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(txt,searchDoctype).getSearchResultList();
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchResults) {
                list.add(searchResult.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list);
            edit.setAdapter(adapter);
            edit.showDropDown();

        } else Notify.Toast("No data found from offline data");
    }
    public static void load(Context context, String searchDoctype, String filters, String reference, String query, AutoCompleteTextView edit) {
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference, query) != null) {
            List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference, query).getSearchResultList();
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchResults) {
                list.add(searchResult.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list);
            edit.setAdapter(adapter);
            edit.showDropDown();

        } else Notify.Toast("No data found from offline data");
    }

    public static void load(Context context, String searchDoctype, String query, AutoCompleteTextView edit) {
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query) != null) {
            List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).getSearchResultList();
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchResults) {
                list.add(searchResult.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list);
            edit.setAdapter(adapter);
            edit.showDropDown();

        } else Notify.Toast("No data found from offline data");
    }

    public static void loadRefQuery(Context context, String searchDoctype, String reference, String query, AutoCompleteTextView edit) {
        if (MainApp.database.searchLinkDao().getSearchRefQueryResponse(searchDoctype, reference, query) != null) {
            List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchRefQueryResponse(searchDoctype, reference, query).getSearchResultList();
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchResults) {
                list.add(searchResult.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list);
            edit.setAdapter(adapter);
            edit.showDropDown();

        } else Notify.Toast("No data found from offline data");
    }

    public static void load(Context context, String searchDoctype, AutoCompleteTextView edit) {
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype) != null) {
            List<SearchResult> searchResults = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype).getSearchResultList();
            List<String> list = new ArrayList<>();
            for (SearchResult searchResult : searchResults) {
                list.add(searchResult.getValue());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list);
            edit.setAdapter(adapter);
            edit.showDropDown();

        } else Notify.Toast("No data found from offline data");
    }

    public static void save(SearchLinkResponse res, String searchDoctype, String filters, String query) {
        res.doctype = searchDoctype;
        res.query = query;
        res.filters = filters;
        if (filters != null) {
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query).doctype;
                String query1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query).query;
                String filters1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query).filters;
                if (doctype.equalsIgnoreCase(searchDoctype) && query.equalsIgnoreCase(query1) && filters.equalsIgnoreCase(filters1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        } else {
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).doctype;
                String query1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).query;
                if (doctype.equalsIgnoreCase(searchDoctype) && query.equalsIgnoreCase(query1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        }
    }

    public static void saveWithRef(SearchLinkResponse res, String searchDoctype, String filters, String reference) {
        res.doctype = searchDoctype;
        res.filters = filters;
        res.reference = reference;
        if (filters != null) {
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference).doctype;
                String filters1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference).filters;
                String reference1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, reference).reference;
                if (doctype.equalsIgnoreCase(searchDoctype) && filters.equalsIgnoreCase(filters1) && reference.equalsIgnoreCase(reference1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        } else {
            if (MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).doctype;
                String reference1 = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).reference;
                if (doctype.equalsIgnoreCase(searchDoctype) && reference.equalsIgnoreCase(reference1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        }
    }

    public static void save(SearchLinkResponse res, String searchDoctype, String filters, String reference, String query) {
        res.doctype = searchDoctype;
        res.filters = filters;
        res.reference = reference;
        res.query = query;
        if (filters != null)
            if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference).doctype;
                String filters1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference).filters;
                String reference1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference).reference;
                String query1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, filters, query, reference).query;
                if (doctype.equalsIgnoreCase(searchDoctype) && filters.equalsIgnoreCase(filters1) && reference.equalsIgnoreCase(reference1) && query.equalsIgnoreCase(query1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        else {
            if (MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference) != null) {
                int id = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).uid;
                String doctype = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).doctype;
                String reference1 = MainApp.database.searchLinkDao().getSearchLinkRefResponse(searchDoctype, reference).reference;
                if (doctype.equalsIgnoreCase(searchDoctype) && reference.equalsIgnoreCase(reference1)) {
                    res.uid = (id);
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                } else {
                    MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
                }
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        }
    }

    public static void save(SearchLinkResponse res, String searchDoctype, String query) {
        res.doctype = searchDoctype;
        res.query = query;
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query) != null) {
            int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).uid;
            String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).doctype;
            String query1 = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype, query).query;
            if (doctype.equalsIgnoreCase(searchDoctype) && query.equalsIgnoreCase(query1)) {
                res.uid = (id);
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        } else {
            MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
        }
    }

    public static void save(SearchLinkResponse res, String searchDoctype) {
        res.doctype = searchDoctype;
        if (MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype) != null) {
            int id = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype).uid;
            String doctype = MainApp.database.searchLinkDao().getSearchLinkResponse(searchDoctype).doctype;
            if (doctype.equalsIgnoreCase(searchDoctype)) {
                res.uid = (id);
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            } else {
                MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
            }
        } else {
            MainApp.database.searchLinkDao().insertSearchLinkResponse(res);
        }
    }
}

