package com.example.erpnext.roomDB.Dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.erpnext.network.serializers.response.SearchLinkResponse;

import java.util.List;

@Dao
public interface SearchLinkDao {
    // Search link
    @Query("SELECT * FROM searchlinkresponse")
    SearchLinkResponse getSearchLinkResponse();

    @Query("SELECT * FROM searchlinkresponse")
    List<SearchLinkResponse> getAllSearchLinkResponse();

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype AND filters LIKE :filters AND `query` LIKE :query")
    SearchLinkResponse getSearchLinkResponse(String doctype, String filters, String query);

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype AND `reference` LIKE :reference AND `query` LIKE :query")
    SearchLinkResponse getSearchRefQueryResponse(String doctype, String reference, String query);


    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype AND filters LIKE :filters AND `query` LIKE :query AND `reference` LIKE :reference")
    SearchLinkResponse getSearchLinkResponse(String doctype, String filters, String query, String reference);

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype AND `query` LIKE :query")
    SearchLinkResponse getSearchLinkResponse(String doctype, String query);

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype  AND filters LIKE :filters AND `reference` LIKE :reference")
    SearchLinkResponse getSearchLinkRefResponse(String doctype, String filters, String reference);

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype AND `reference` LIKE :reference")
    SearchLinkResponse getSearchLinkRefResponse(String doctype, String reference);

    @Query("SELECT * FROM searchlinkresponse WHERE doctype LIKE :doctype")
    SearchLinkResponse getSearchLinkResponse(String doctype);

    @Insert(onConflict = REPLACE)
    void insertSearchLinkResponse(SearchLinkResponse res);

}
