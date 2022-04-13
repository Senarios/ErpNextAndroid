package com.example.erpnext.utils;

public class RequestCodes {

    public static final int WRITE_PERMISSION = 1;
    public static final int GROUP_IMAGE = 2;
    public static final int GROUP_IMAGE_CAM = 3;
    public static final int ADD_NEW_LEAD = 4;
    public static final int ATTACH_LOGO_CAM = 5;
    public static final int PICKFILE_RESULT_CODE = 6;
    public static final int ATTACH_LOGO_GROUP = 7;
    public static final int ATTACH_LOGO_GROUP_CAM = 8;
    public static final int REQUEST_PHONE_CALL = 8;
    public static final int BUFFER_SIZE = 9;
    public static final int AUDIO_PERMISSION_CODE = 10;
    public static final int PICK_PHOTO_GALLERY = 11;
    public static final int PICK_PHOTO_CAMERA = 12;
    public static final int LOCATIONS = 13;
    public static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final int VIDEO_TRIMMER = 14;
    public static final int MIC_PERMISSION_REQUEST_CODE = 15;
    public static final int CREATE_NEW_POS_PROFILE = 16;
    public static final int CREATE_NEW_POS_OPENING = 17;
    public static final int CREATE_NEW_POS_CLOSING = 18;
    public static final int CREATE_NEW_LOYALTY_PROGRAM = 19;
    public static final int CREATE_NEW_PURCHAS_RECEIPT = 20;
    public static final int ADD_ITEM_PRICE = 21;


    public static final int CREATE_NEW_WAREHOUSE = 22;
    public static final int ADD_ITEM = 23;
    public static final int ADD_OPPORTUNITY = 24;
    public static final int ADD_CUSTOMER_GROUP = 25;
    public static final int LOCATION_REQUEST_CODE = 26;
    public static final int ERROR_DIALOGE_REQUEST = 27;
    public static final int ADD_CUSTOMER = 28;
    public static final int ADD_RECONCILIATION = 29;
    public static final int ADD_CONTACT = 30;
    public static final int ADD_SALES_PERSON = 31;


    public class API {
        public static final int SIGNUP = -1;
        public static final int FB_SIGNUP = -2;
        public static final int LOGIN = 1;
        public static final int COMPLETE_ORDER = 2;
        public static final int GET_INVOICES = 3;
        public static final int DELETE_INVOICE = 4;
        public static final int GET_PROJECT_GROUPS = 5;
        public static final int GET_DOC = 6;
        public static final int CREATE_GROUP = 7;
        public static final int PROJECT_AVAILABLE_STAFF = 8;
        public static final int GROUP_AVAILABLE_STAFF = 9;
        public static final int TASK_AVAILABLE_STAFF = 10;
        public static final int ASSIGN_PROJECT_STAFF = 11;
        public static final int ASSIGN_GROUP_STAFF = 12;
        public static final int ASSIGN_TASK_STAFF = 13;
        public static final int REMOVE_TAG = 14;
        public static final int REMOVE_ASSIGNEE = 15;
        public static final int REMOVE_ATTACHMENT = 16;
        public static final int AVAILABLE_TASK_STAFF = 17;
        public static final int REMOVE_COMMENT = 18;
        public static final int GET_POS_ITEMS = 19;
        public static final int GET_ITEM_DETAILS = 20;
        public static final int UPDATE_TASK = 21;
        public static final int UPDATE_PROJECT = 22;
        public static final int UPDATE_GROUP = 23;
        public static final int GET_PROFILE_DOC = 24;
        public static final int CHECK_OPENING_ENTRY = 25;
        public static final int GET_TAG_SUGGESTIONS = 26;
        public static final int SEND_FIRST_MESSAGE = 27;
        public static final int SEND_Mail = 28;
        public static final int GET_NEW_MESSAGES = 29;
        public static final int DELETE_MESSAGES = 30;
        public static final int RUN_DOC = 31;
        public static final int GET_MORE_MESSAGES = 32;
        public static final int SEND_ATTACHMENT = 33;
        public static final int DELETE_ALL_CONVERSATION = 34;
        public static final int SEND_FIRST_ATTACHMENT = 35;
        public static final int MESSAGE_SEEN = 36;
        public static final int STATUS = 37;
        public static final int LOGOUT = 38;
        public static final int MY_GROUPS = 39;
        public static final int MY_TASKS = 40;
        public static final int MANAGEMENT_UPDATES = 41;
        public static final int PGT_STATUS = 42;
        public static final int AUDIO_ACCESS_TOKEN = 42;
        public static final int SAVE_EDITED_DOC = 43;
        public static final int MENU = 44;
        public static final int ITEM_DETAIL = 45;
        public static final int DOCTYPE = 46;
        public static final int SP_REPORT_SUMMARY = 4896;
        public static final int REPORT_VIEW = 47;
        public static final int SEARCH_RECONCILIATION_REPORT_VIEW = 4007;
        public static final int SEARCH_PURCHASE_REPORT_VIEW = 4357;
        public static final int SEARCH_STOCK_REPORT_VIEW = 467;
        public static final int SEARCH_REPORT_VIEW = 4747;
        public static final int SHARJEEL = 309;
        public static final int LINK_SEARCH = 48;
        public static final int TABLE_SEARCH = 49;
        public static final int SAVE_DOC = 50;
        public static final int ADD_ASSIGNEES = 51;
        public static final int ADD_TAG = 52;
        public static final int UPLOAD_ATTACHMENT = 53;
        public static final int ADD_COMMENT = 54;
        public static final int LINK_SEARCH_CUSTOMER = 55;
        public static final int LINK_SEARCH_ITEM_GROUP = 56;
        public static final int LINK_SEARCH_ITEM = 57;
        public static final int LINK_SEARCH_UMO = 58;
        public static final int LINK_SEARCH_INVOICE = 59;
        public static final int SAVE_LOYALTY = 60;
        public static final int CHANGE_STATUS = 61;
        public static final int STOKE_BALANCE_REPORT = 62;
        public static final int GENERATE_STOKE_REPORT = 63;
        public static final int SEARCH_ITEM = 64;
        public static final int SUPPLIER_LINK_SEARCH = 65;
        public static final int SUPPLIER_ADDRESS_LINK_SEARCH = 66;
        public static final int SUPPLIER_SHIPPING_ADDRESS_LINK_SEARCH = 67;
        public static final int SUPPLIER_BILLING_ADDRESS_LINK_SEARCH = 68;
        public static final int CONTACT_PERSON_LINK_SEARCH = 69;
        public static final int ACCEPTED_WAREHOUSE_SEARCH = 70;
        public static final int REJECTED_WAREHOUSE_LINK_SEARCH = 71;
        public static final int CURRENCY_LINK_SEARCH = 72;
        public static final int SUPPLIER_DELEIVERY_LINK_SEARCH = 73;
        public static final int SUPPLIER_CONTACT_PERSON_LINK_SEARCH = 74;
        public static final int PRICE_LIST_LINK_SEARCH = 75;
        public static final int LINK_SEARCH_SOURCE_WAREHOUSE = 76;
        public static final int LINK_SEARCH_TARGET_WAREHOUSE = 77;
        public static final int LINK_SEARCH_STOCK_ENTRY_TYPE = 78;
        public static final int PARTY_DETAILS = 79;
        public static final int LINK_SEARCH_SHIPPING_ADDRESS = 80;
        public static final int LINK_SEARCH_COMPANY_ADDRESS_NAME = 81;
        public static final int ITEM_CODE_SEARCH = 179;
        public static final int PURCHASE_INVOICE = 180;
        public static final int INVOICE_DETAIL = 181;
        public static final int LINK_SEARCH_PARENT_TERRITORY = 182;
        public static final int LINK_SEARCH_TERRITORY_MANAGER = 183;

        public static final int SEARCH_WAREHOUSE = 290;
        public static final int WAREHOUSE_ITEMS = 291;
        public static final int DIFF_ACCOUNT = 292;
        public static final int DOC_RECEIPT = 293;
        public static final int FETCH_VALUES = 294;

        public static final int ACCOUNT_SEARCH = 182;
        public static final int WAREHOUSE_TYPE = 183;
        public static final int PARENT_WAREHOUSE = 184;
        public static final int DEFAULT_IN_TRANSIT_WAREHOUSE = 185;
        public static final int COMPANY = 186;

        public static final int LANDED_COST_ITEMS = 187;
        public static final int EXPENSE_ACCOUNT = 188;
        public static final int ITEM_GROUP = 189;
        public static final int DEFAULT_UNIT_OF_MEASURE = 190;
        public static final int GENERATE_STOCK_SUMMARY_REPORT = 82;


        public static final int SEARCH_SALUATION = 191;
        public static final int SEARCH_DESIGNATION = 192;
        public static final int SEARCH_GENDER = 193;
        public static final int SEARCH_SOURCH = 194;
        public static final int SEARCH_CAMPAIGN_NAME = 195;
        public static final int NEXT_CONTACT_BY = 196;
        public static final int SEARCH_COUNTRY = 197;
        public static final int LEAD_OWNER = 198;
        public static final int SEARCH_PARTY = 199;
        public static final int OPPORTUNITY_FROM = 200;
        public static final int OPPORTUNITY_TYPE = 201;
        public static final int SALES_STAGE = 202;
        public static final int CONVERTED_BY = 203;
        public static final int SEARCH_LEAD = 204;
        public static final int PARENT_CUSTOMER_GROUP = 205;
        public static final int DEFAULT_PRICE_LIST = 206;
        public static final int DEFAULT_PAYMENT_TERMS = 207;

        public static final int TERRITORY_TARGETS_ITEM_GROUP = 211;
        public static final int TERRITORY_TARGETS_FISCAL_YEAR = 212;
        public static final int TERRITORY_TARGETS_TARGET_DISTRIBUTION = 213;

        public static final int SEARCH_TERRITORY = 214;
        public static final int CUSTOMER_GROUP = 215;
        public static final int SEARCH_EMAIL = 216;
        public static final int LINK_DOC_TYPE = 217;
        public static final int LINK_NAME = 218;
        public static final int GET_VALUE = 219;
        public static final int SEARCH_PARENT_SALES_PERSON = 220;
        public static final int SEARCH_EMPLOYEE = 221;
        public static final int GET_DEPARTMENT = 222;
        public static final int GET_STOCK_SUMMARY_LIST = 223;
    }

    public class INTENT {
        public static final int POLICE_REPORT_EVIDENCE = 8;
    }
}