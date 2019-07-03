//package android.example.cs496.ui.main.fragment1;
//
//import android.content.ContentProviderOperation;
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.OperationApplicationException;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.RemoteException;
//import android.provider.ContactsContract;
//import android.provider.ContactsContract.CommonDataKinds;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class phoneBookLoader {
//
//    private static List<RecyclerItem> datas = new ArrayList<>();
//    private static String[] projection = new String[]{
//            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//            ContactsContract.Contacts.PHOTO_ID,
//            ContactsContract.Contacts._ID,
//    };
//    private static String[] phoneProjection = {
//            CommonDataKinds.Phone.NUMBER,
//    };
//    private static String[] emailProjection = {
//            CommonDataKinds.Email.ADDRESS
//    };
//    private static String[] groupProjection = {
//            CommonDataKinds.Organization.DATA
//    };
//
//    public static List<RecyclerItem> getData(Context context) {
//
//        ContentResolver resolver = context.getContentResolver();
//        Uri phoneUri = ContactsContract.Contacts.CONTENT_URI;
//
//        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
//
//        Cursor cursor = resolver.query(phoneUri,
//                projection,
//                null,
//                null,
//                sortOrder);
//
//        LinkedHashSet<RecyclerItem> hashlist = new LinkedHashSet<>();
//
//        // cursor에 데이터 존재여부
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                RecyclerItem data = new RecyclerItem();
//                long photo_id = cursor.getLong(1);
//                long person_id = cursor.getLong(2);
//                //data.setPhone(cursor.getString(0));
//                data.setName(cursor.getString(0));
//
//                String phone = "";
//                Cursor phoneCursor = null;
//
//                try {
//                    //현재 가져온 이름을 사용하여 E-mail주소가 등록되어있는지 검색
//                    phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            phoneProjection,
//                            ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + "=" + person_id,
//                            null, null);
//
//                    while(phoneCursor.moveToNext()) {
//                        phone = phoneCursor.getString(0);
//                        data.setPhone(phone);
//                    }
//                }
//                catch (Exception e) {
//                    Log.e("[GetPhonenumberAdapter] getContactData", e.toString());
//
//                } finally {
//                    phoneCursor.close();
//                }
//
//                String email = "";
//                Cursor emailCursor = null;
//
//                try {
//                    //현재 가져온 이름을 사용하여 E-mail주소가 등록되어있는지 검색
//                    emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            emailProjection,
//                            ContactsContract.CommonDataKinds.Email.RAW_CONTACT_ID + "=" + person_id,
//                            null, null);
//
//                    while(emailCursor.moveToNext()) {
//                        email = emailCursor.getString(0);
//                        data.setEmail(email);
//                    }
//                }
//                catch (Exception e) {
//                    Log.e("[GetPhonenumberAdapter] getContactData", e.toString());
//                }
//                finally {
//                    emailCursor.close();
//                }
////                String group = "";
////                Cursor groupCursor = null;
////
////                try {
////                    //현재 가져온 이름을 사용하여 E-mail주소가 등록되어있는지 검색
////                    groupCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
////                            groupProjection,
////                            CommonDataKinds.Organization.CONTACT_ID + "=" + person_id,
////                            null, null);
////
////                    while(groupCursor.moveToNext()) {
////                        group = groupCursor.getString(0);
////                        data.setGroup(group);
////                    }
////                }
////                catch (Exception e) {
////                    Log.e("[GetPhonenumberAdapter] getContactData1", e.toString());
////                }
////                finally {
////                    groupCursor.close();
////                }
////                Cursor clsEmailcursor = context.getContentResolver().query(
////                        CommonDataKinds.Email.CONTENT_URI,
////                        emailProjection,
////                        CommonDataKinds.Phone.CONTACT_ID + "=" + (String.valueOf(person_id)),
////                        null, null
////                );
////                while(clsEmailcursor.moveToNext())data.setEmail(clsEmailcursor.getString(0));
//
////                Cursor clsOrgCursor = context.getContentResolver().query(
////                        ContactsContract.Data.CONTENT_URI,
////                        emailProjection,
////                        CommonDataKinds.Phone.CONTACT_ID + "=" + (String.valueOf(person_id)),
////                        null, null
////                );
////
////                while(clsOrgCursor.moveToNext())data.setGroup(clsOrgCursor.getString(clsOrgCursor.getColumnIndex(CommonDataKinds.Organization.DATA)));
////                clsOrgCursor.close();
//                data.setImg(photo_id);
//                data.setPersonId(person_id);
//
//                hashlist.add(data);
//            }
//        }
//        cursor.close(); // 닫지 않으면 계속 열려있음.
//
//        datas = new ArrayList<>(hashlist);
//        for(int i=0 ; i<datas.size() ; i++)
//            datas.get(i).setId(i);
//        //
//        //Collections.sort(datas, new AscendingInteger());
//        return datas;
//    }
//
//    final public static Pattern PAT_MAIL = Pattern.compile("^[\\._a-z0-9\\-]+@[\\._a-z0-9\\-]+\\.[a-z]{2,}$");
//    private static boolean isEmail(String email)
//    {
//        return email != null && PAT_MAIL.matcher(email).matches();
//    }
//
//    public static boolean EditContactsInfo(Context context, RecyclerItem new_item) {
//        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
//
//        String phoneWhere = ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = ? ";
//        String emailWhere = CommonDataKinds.Email.RAW_CONTACT_ID + " = ? ";
//
//        System.out.println(new_item.getName());
//        System.out.println(new_item.getPhone());
//        System.out.println(new_item.getEmail());
//
//        String[] params = new String[] {String.valueOf(new_item.getPersonId())};
//        if(!new_item.getName().isEmpty())
//            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//                .withSelection(phoneWhere, params)
//                .withValue(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, new_item.getName())
//                .build());
//        if(!new_item.getPhone().isEmpty())
//            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//                .withSelection(phoneWhere, params)
//                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, new_item.getPhone())
//                .build());
//        if(!new_item.getEmail().isEmpty())
//            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//                .withSelection(emailWhere, params)
//                .withValue(CommonDataKinds.Email.ADDRESS, new_item.getEmail())
//                .build());
//        try {
//            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
////        if(new_item.getPhone() == "")return false;
////
////        String phoneNumber = new_item.getPhone();
////        long person_id = new_item.getPersonId();
////
////
////        ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<>();
////
////        String where = ContactsContract.Data.CONTACT_ID+"= ?";
////
////        if(new_item.getName() != ""){
////            String[] nameParams = new String[] {
////                    String.valueOf(new_item.getId())
////            };
////            Cursor nameCursor = context.getContentResolver().query (
////                ContactsContract.Data.CONTENT_URI,
////                    projection,
////                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + person_id,
////                    null, null
////            );
////            if ( nameCursor.getCount() > 0){
////                ops.add(ContentProviderOperation.newUpdate(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
////                        .withSelection(where, nameParams)
////                        .withValue(CommonDataKinds.Phone.DISPLAY_NAME, new_item.getName())
////                        .build());
////            }
////            else {
////                ContentValues cValues = new ContentValues();
////                cValues.put(ContactsContract.Data.CONTACT_ID, person_id);
////                cValues.put(ContactsContract.Data.DISPLAY_NAME, new_item.getName());
////
////                ops.add(android.content.ContentProviderOperation.newInsert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
////                        .withValues(cValues)
////                        .build());
////            }
////        }
////        else return false;
////
////        if(new_item.getPhone() != ""){
////            String[] phoneNumparams = new String[] {
////                    String.valueOf(new_item.getId()),
////            };
////
////            Cursor phoneNumCursor = context.getContentResolver().query (
////                    ContactsContract.Data.CONTENT_URI,
////                    phoneProjection,
////                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + person_id,
////                    null, null
////            );
////
////            if(phoneNumCursor.getCount() > 0){
////                ops.add(android.content.ContentProviderOperation.newUpdate(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
////                        .withSelection(where, phoneNumparams)
////                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, new_item.getPhone())
////                        .withValue(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE)
////                        .build());
////            }
////            else {
////                ContentValues cValues = new ContentValues();
////                cValues.put(ContactsContract.Data.CONTACT_ID, person_id);
////                cValues.put(CommonDataKinds.Phone.NUMBER, new_item.getPhone());
////
////                ops.add(android.content.ContentProviderOperation.newInsert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
////                        .withValues(cValues)
////                        .build());
////            }
////        }
////        else return false;
////
////        String[] emailParams = new String[] {
////                String.valueOf(new_item.getId()),
////        };
////
////        Cursor emailCursor = context.getContentResolver().query (
////                ContactsContract.Data.CONTENT_URI,
////                emailProjection,
////                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + person_id,
////                null, null
////        );
////
////        if(emailCursor.getCount() > 0){
////            ops.add(android.content.ContentProviderOperation.newUpdate(ContactsContract.CommonDataKinds.Email.CONTENT_URI)
////                    .withSelection(where, emailParams)
////                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, new_item.getEmail())
////                    .build());
////        }
////        else {
////            ContentValues cValues = new ContentValues();
////            cValues.put(ContactsContract.Data.CONTACT_ID, person_id);
////            cValues.put(CommonDataKinds.Email.ADDRESS, new_item.getEmail());
////
////            ops.add(android.content.ContentProviderOperation.newInsert(ContactsContract.CommonDataKinds.Email.CONTENT_URI)
////                    .withValues(cValues)
////                    .build());
////        }
////
////        String[] orgParams = new String[] {
////                String.valueOf(new_item.getId()),
////                CommonDataKinds.Email.CONTENT_ITEM_TYPE
////        };
////
//////        Cursor orgCursor = context.getContentResolver().query (
//////                ContactsContract.Data.CONTENT_URI,
//////                null,
//////                where,
//////                orgParams, null
//////        );
//////
//////        if(orgCursor.getCount() > 0){
//////            ops.add(android.content.ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//////                    .withSelection(where, orgParams)
//////                    .withValue(CommonDataKinds.Organization.TITLE, new_item.getGroup())
//////                    .build());
//////        }
//////        else {
//////            ContentValues cValues = new ContentValues();
//////            cValues.put(ContactsContract.Data.CONTACT_ID, person_id);
//////            cValues.put(CommonDataKinds.Organization.DATA, new_item.getGroup());
//////
//////            ops.add(android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//////                    .withValues(cValues)
//////                    .build());
//////        }
////
////        try {
////            if(ops.size() > 0){
////                context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
////                return true;
////            }
////        } catch (RemoteException e) {
////            e.printStackTrace();
////        } catch (OperationApplicationException e) {
////            e.printStackTrace();
////        }
////        return false;
////    }
////}