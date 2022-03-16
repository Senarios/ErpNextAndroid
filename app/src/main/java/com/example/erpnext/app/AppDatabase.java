package com.example.erpnext.app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.erpnext.models.AddCustomerOfflineModel;
import com.example.erpnext.models.MyTaskOfflineModel;
import com.example.erpnext.models.PendingEditPOS;
import com.example.erpnext.models.PendingInvoiceAction;
import com.example.erpnext.models.PendingLoyalty;
import com.example.erpnext.models.PendingOPE;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.models.StockEntryOfflineModel;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.DocDetailResponse;
import com.example.erpnext.network.serializers.response.DocTypeResponse;
import com.example.erpnext.network.serializers.response.GetItemDetailResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.MenuResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.Dao.AddCustomerDao;
import com.example.erpnext.roomDB.Dao.CheckOPEDao;
import com.example.erpnext.roomDB.Dao.DocProfileDao;
import com.example.erpnext.roomDB.Dao.DoctypeDao;
import com.example.erpnext.roomDB.Dao.ItemDetailsDao;
import com.example.erpnext.roomDB.Dao.ItemsDao;
import com.example.erpnext.roomDB.Dao.MenuDao;
import com.example.erpnext.roomDB.Dao.MenuItemsDao;
import com.example.erpnext.roomDB.Dao.MyTaskDao;
import com.example.erpnext.roomDB.Dao.PendingCancelInvoiceDao;
import com.example.erpnext.roomDB.Dao.PendingEditPOSDao;
import com.example.erpnext.roomDB.Dao.PendingLoyaltyDao;
import com.example.erpnext.roomDB.Dao.PendingOPEDao;
import com.example.erpnext.roomDB.Dao.PendingOrderDao;
import com.example.erpnext.roomDB.Dao.ProfileDocDetailDao;
import com.example.erpnext.roomDB.Dao.ReportViewDao;
import com.example.erpnext.roomDB.Dao.SearchLinkDao;
import com.example.erpnext.roomDB.Dao.StockEntryDao;
import com.example.erpnext.utils.Constants;

@Database(entities = {MenuResponse.class, ItemResponse.class,
        CheckOpeningEntryResponse.class, DocTypeResponse.class,
        ReportViewResponse.class, GetProfileDocResponse.class,
        GetItemsResponse.class, SearchLinkResponse.class,
        PendingOrder.class, PendingOPE.class, PendingLoyalty.class,
        DocDetailResponse.class, PendingEditPOS.class,
        PendingInvoiceAction.class, MyTaskOfflineModel.class,
        AddCustomerOfflineModel.class,StockEntryOfflineModel.class, GetItemDetailResponse.class}
        , version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DATABASE)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
//                            .setJournalMode(JournalMode.TRUNCATE)
                            .build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract MenuDao menuDao();

    public abstract CheckOPEDao checkOPEDao();

    public abstract DoctypeDao doctypeDao();

    public abstract MenuItemsDao menuItemsDao();

    public abstract DocProfileDao docProfileDao();

    public abstract ReportViewDao reportViewDao();

    public abstract ItemsDao itemsDao();

    public abstract SearchLinkDao searchLinkDao();

    public abstract PendingOrderDao pendingOrderDao();

    public abstract PendingOPEDao pendingOPEDao();

    public abstract PendingLoyaltyDao pendingLoyaltyDao();

    public abstract ProfileDocDetailDao profileDocDetailDao();

    public abstract PendingEditPOSDao pendingEditPOSDao();

    public abstract PendingCancelInvoiceDao pendingCancelInvoiceDao();

    public abstract ItemDetailsDao itemDetailsDao();

    public abstract MyTaskDao myTaskDao();

    public abstract StockEntryDao StockEntryDao();

    public abstract AddCustomerDao addCustomerDao();
}
