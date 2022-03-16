package com.example.erpnext.roomDB.data;

import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.DocDetailResponse;
import com.example.erpnext.network.serializers.response.DocTypeResponse;
import com.example.erpnext.network.serializers.response.GetItemDetailResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.MenuResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public static void saveMenuItems(ItemResponse res) {
        if (MainApp.database.menuItemsDao().getMenuItems(res.itemName) != null) {
            int id = MainApp.database.menuItemsDao().getMenuItems(res.itemName).getUid();
            res.setUid(id);
            MainApp.database.menuItemsDao().insertRetailItems(res);
        } else MainApp.database.menuItemsDao().insertRetailItems(res);
    }

    public static void saveMenu(MenuResponse res) {
        if (MainApp.database.menuDao().getMenu() != null) {
            int id = MainApp.database.menuDao().getMenu().uid;
            res.uid = id;
            MainApp.database.menuDao().insertMenu(res);
        } else MainApp.database.menuDao().insertMenu(res);
    }

    public static void saveDoc(DocTypeResponse res, String docType) {
        res.doctype = docType;
        if (MainApp.database.doctypeDao().getDocTypeResponse(docType) != null) {
            int id = MainApp.database.doctypeDao().getDocTypeResponse(docType).uid;
            res.uid = (id);
            MainApp.database.doctypeDao().insertDocTypeResponse(res);
        } else MainApp.database.doctypeDao().insertDocTypeResponse(res);
    }

    public static void saveMoreReportView(ReportViewResponse res, String docType) {
        if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
            int id = MainApp.database.reportViewDao().getReportViewResponse(docType).uids;
            String doctype = MainApp.database.reportViewDao().getReportViewResponse(docType).doctype;

            ReportViewResponse reportViewResponse = MainApp.database.reportViewDao().getReportViewResponse(docType);
            reportViewResponse.getReportViewMessage().getValues().addAll(res.getReportViewMessage().getValues());
            reportViewResponse.uids = (id);
            MainApp.database.reportViewDao().insertReportViewResponse(reportViewResponse);
        }
    }

    public static void saveReportView(ReportViewResponse res, String docType) {
        res.doctype = docType;
        if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
            int id = MainApp.database.reportViewDao().getReportViewResponse(docType).uids;
            res.uids = (id);
            MainApp.database.reportViewDao().insertReportViewResponse(res);
        } else MainApp.database.reportViewDao().insertReportViewResponse(res);
    }

    public static List<List<String>> loadReportItem(String docType) {
        List<List<String>> lists = new ArrayList<>();
        if (MainApp.database.doctypeDao().getDocTypeResponse(docType) != null) {

            if (!Utils.isNetworkAvailable()) {
                if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
                    ReportViewResponse response = MainApp.database.reportViewDao().getReportViewResponse(docType);
                    lists = (response.getReportViewMessage().getValues());
                }
            }
        }
        return lists;
    }

    public static List<List<String>> getReportView(String docType) {
        List<List<String>> lists = new ArrayList<>();
        if (!Utils.isNetworkAvailable()) {
            if (MainApp.database.reportViewDao().getReportViewResponse(docType) != null) {
                ReportViewResponse response = MainApp.database.reportViewDao().getReportViewResponse(docType);
                lists = (response.getReportViewMessage().getValues());
            }
        }
        return lists;
    }

    public static void saveProfileDoc(GetProfileDocResponse res) {
//        res.name=res.getProfileDoc().getName();
        if (MainApp.database.docProfileDao().getProfileDocResponse(res.getProfileDoc().getName()) != null) {
            int id = MainApp.database.docProfileDao().getProfileDocResponse().uid;
            res.uid = (id);
            MainApp.database.docProfileDao().insertDocProfileResponse(res);
        } else MainApp.database.docProfileDao().insertDocProfileResponse(res);
    }

    public static void savePOSItems(GetItemsResponse res, String item_group, ProfileDoc profileDoc) {
        res.itemGroup = item_group;
        res.posProfile = profileDoc.getName();
        if (MainApp.database.itemsDao().getItemResponse(item_group) != null) {
            int id = MainApp.database.itemsDao().getItemResponse(item_group).uid;
            String itemGroup = MainApp.database.itemsDao().getItemResponse(item_group).itemGroup;
            if (itemGroup.equalsIgnoreCase(item_group)) {
                res.uid = (id);
                MainApp.database.itemsDao().insertGetItemResponse(res);
            } else {
                MainApp.database.itemsDao().insertGetItemResponse(res);
            }
        } else {
            MainApp.database.itemsDao().insertGetItemResponse(res);
        }
    }

    public static void saveMorePOSItems(GetItemsResponse res, String item_group) {
        if (MainApp.database.itemsDao().getItemResponse(item_group) != null) {
            int id = MainApp.database.itemsDao().getItemResponse(item_group).uid;
            GetItemsResponse response = MainApp.database.itemsDao().getItemResponse(item_group);
            response.getItemMessage().getCartItemList().addAll(res.getItemMessage().getCartItemList());
            response.uid = (id);
            MainApp.database.itemsDao().insertGetItemResponse(response);
        }
    }

    public static void saveProfileDetail(DocDetailResponse res) {
        String name = res.getDocs().get(0).getName();
        res.profileName = name;
        if (MainApp.database.profileDocDetailDao().getProfileDetail(name) != null) {
            int id = MainApp.database.profileDocDetailDao().getProfileDetail(name).uid;
            res.uid = (id);
            MainApp.database.profileDocDetailDao().insert(res);
        } else MainApp.database.profileDocDetailDao().insert(res);
    }

    public static DocDetailResponse getProfileDetail(String profilename) {
        if (MainApp.database.profileDocDetailDao().getProfileDetail(profilename) != null) {
            DocDetailResponse response = MainApp.database.profileDocDetailDao().getProfileDetail(profilename);
            return response;
        } else {
            Notify.Toast("No profile found offline");
            return null;
        }
    }

    public static void saveItemDetail(GetItemDetailResponse res) {
        String itemCode = res.getItemDetail().getItemCode();
        String warehouse = res.getItemDetail().getWarehouse();
        if (MainApp.database.itemDetailsDao().getItemDetail(itemCode, warehouse) != null) {
            int id = MainApp.database.itemDetailsDao().getItemDetail(itemCode, warehouse).uid;
            res.uid = (id);
            MainApp.database.itemDetailsDao().insert(res);
        } else MainApp.database.itemDetailsDao().insert(res);
    }

    public static GetItemDetailResponse getItemDetail(String itemCode, String warehouse) {
        if (MainApp.database.itemDetailsDao().getItemDetail(itemCode, warehouse) != null) {
            GetItemDetailResponse response = MainApp.database.itemDetailsDao().getItemDetail(itemCode, warehouse);
            return response;
        } else {
            Notify.Toast("This item is not available in offline");
            return null;
        }
    }

    public static void saveCheckOPE(CheckOpeningEntryResponse res) {
        if (MainApp.database.checkOPEDao().getCheckOpeningEntries() != null) {
            int id = MainApp.database.checkOPEDao().getCheckOpeningEntries().uid;
            res.uid = (id);
            MainApp.database.checkOPEDao().insertCheckOpeningEntry(res);
        } else MainApp.database.checkOPEDao().insertCheckOpeningEntry(res);
    }
}
