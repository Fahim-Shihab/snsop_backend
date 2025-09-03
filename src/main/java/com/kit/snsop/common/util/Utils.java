package com.kit.snsop.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kit.snsop.beneficiary.enums.ErrorCode;
import com.kit.snsop.common.payload.ServiceError;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author DELL
 */
public class Utils {
    public static void copyBean(final Object from, final Object to) {
        Map<String, Field> fromFields = analyze(from);
        Map<String, Field> toFields = analyze(to);
        fromFields.keySet().retainAll(toFields.keySet());
        for (Map.Entry<String, Field> fromFieldEntry : fromFields.entrySet()) {
            final String name = fromFieldEntry.getKey();
            final Field sourceField = fromFieldEntry.getValue();
            final Field targetField = toFields.get(name);
            if (targetField.getType().isAssignableFrom(sourceField.getType())) {
                sourceField.setAccessible(true);
                if (Modifier.isFinal(targetField.getModifiers())) {
                    continue;
                }
                targetField.setAccessible(true);
                try {
                    Object ob = sourceField.getType().equals(String.class)
                            && sourceField.get(from) != null
                            && ((String) sourceField.get(from)).trim() != "" ?
                            ((String) sourceField.get(from)).trim().toUpperCase() : sourceField.get(from);
                    targetField.set(to, ob);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Can't access field!");
                }
            }
        }
    }

    public static Integer getIntegerFromObject(Object object) {
        if(object == null) {
            return null;
        }
        if(object instanceof Integer) {
            return ((Integer) object).intValue();
        } else if(object instanceof Long) {
            return ((Long)object).intValue();
        } else if(object instanceof BigDecimal) {
            return ((BigDecimal)object).intValue();
        }else if(object instanceof BigInteger) {
            return ((BigInteger)object).intValue();
        } else if(object instanceof String) {
            try {
                return Integer.parseInt((String) object);
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
        }
        else {
            return 0;
        }
    }

    private static Map<String, Field> analyze(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        Map<String, Field> map = new TreeMap<String, Field>();
        Class<?> current = object.getClass();
        while (current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!map.containsKey(field.getName())) {
                        map.put(field.getName(), field);
                    }
                }
            }
            current = current.getSuperclass();
        }

        return map;
    }

    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    /**
     * @return Timestamp object of current date and time
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static long getElapsedTimeFromNow(Timestamp fromTime) {
        return System.currentTimeMillis() - fromTime.getTime();
        //Timestamp currentTimestamp = getCurrentTimestamp();
        //return currentTimestamp.getTime() - fromTime.getTime();
    }

    public static String getNewUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getBanglaDigitsFromEnglishDigits(String englishDigits) {
        String banglaDigits = "";

        HashMap hm = new HashMap();
        hm.put("1", new String("\u09E7"));
        hm.put("2", new String("\u09E8"));
        hm.put("3", new String("\u09E9"));
        hm.put("4", new String("\u09EA"));
        hm.put("5", new String("\u09EB"));
        hm.put("6", new String("\u09EC"));
        hm.put("7", new String("\u09ED"));
        hm.put("8", new String("\u09EE"));
        hm.put("9", new String("\u09EF"));
        hm.put("0", new String("\u09E6"));

        if (englishDigits != null && englishDigits.length() > 0) {
            for (int i = 0; i < englishDigits.length(); i++) {
                if (hm.containsKey(englishDigits.substring(i, i + 1))) {
                    banglaDigits += hm.get(englishDigits.substring(i, i + 1));
                } else {
                    banglaDigits += englishDigits.substring(i, i + 1);
                }
            }
        }

        return banglaDigits;
    }

    public static String toBanglaDate(Timestamp t) {
        if (t == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return getBanglaDigitsFromEnglishDigits(sdf.format(new Date(t.getTime())));
    }

    public static String getFormattedDate(String format, Timestamp t) {
        if (t == null) {
            return "";
        }

        if (isEmpty(format)) {
            format = "dd/MM/yyyy";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(t.getTime()));
    }

    public static Timestamp getStringToTimestamp(String stringDate) {
        Timestamp dateAsTimestamp = null;
        try {
            DateFormat formatter;
            Date date;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(stringDate);
            dateAsTimestamp = new Timestamp(date.getTime());
        } catch (Exception x) {
            x.printStackTrace();
        }
        return dateAsTimestamp;
    }

    public static String getDateAsString(Date givenDate) {

        String dateAsString = "";
        try {
            SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder nowMMDDYYYY = new StringBuilder(dateformatMMDDYYYY.format(givenDate));
            dateAsString = nowMMDDYYYY.toString();
        } catch (Exception x) {
            x.printStackTrace();
        }
        return dateAsString;
    }


    public static String getDateToString(Date date) {
        if (date == null) {
            return "0000-00-00";
        }

        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public static String getDateToFormatString(Date date, String pattern) {
        if (date == null || pattern == null) {
            return "0000-00-00";
        }

        return (new SimpleDateFormat(pattern)).format(date);
    }

    public static boolean isLeapYear(int y) {
        if (y % 400 == 0) {
            return true;
        }
        if (y % 100 == 0) {
            return false;
        }
        return y % 4 == 0;
    }

    public static String getTimestampToString(Timestamp ts) {
        if (ts == null) {
            return "0000-00-00";
        }

        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date(ts.getTime()));
    }

    public static boolean isDateEqual(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return true;
        }
        if (d1 == null || d2 == null) {
            return false;
        }

        return getDateToString(d1).equalsIgnoreCase(getDateToString(d2));
    }

    public static boolean isDateEqual(Date d, Timestamp t) {
        if (d == null && t == null) {
            return true;
        }
        if (d == null || t == null) {
            return false;
        }

        return getDateToString(d).equalsIgnoreCase(getTimestampToString(t));
    }

    public static boolean isDateEqual(Timestamp t, Date d) {
        return isDateEqual(d, t);
    }

    public static boolean isDateEqual(Timestamp t1, Timestamp t2) {
        return getTimestampToString(t1).equalsIgnoreCase(getTimestampToString(t2));
    }

    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isInList(String s, List<String> args, boolean ignoreCase) {
        if (s == null) {
            return false;
        }

        if (Utils.isEmptyStringList(args)) {
            return false;
        }

        for (String arg : args) {
            if (arg == null) {
                continue;
            }

            if (ignoreCase && arg.equalsIgnoreCase(s)) {
                return true;
            } else if (arg.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInList(String s, Object... args) {
        if (s == null) {
            return false;
        }

        if (args.length < 1) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {

            System.out.println(args[i]);
            if (args[i] == null) {
                continue;
            }

            if (args[i].toString().equals(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInList(Integer l, Integer... args) {
        if (l == null || l == 0L) {
            return false;
        }

        if (args.length < 1) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || ((Integer) args[i] == 0L)) {
                continue;
            }

            if (((Integer) args[i]).equals(l)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInListIgnoreCase(String s, Object... args) {
        if (s == null) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }

            if (args[i].toString().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInList(Long s, Long... args) {
        if (s == null) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            if (args[i].longValue() == s.longValue()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInList(Short s, Short... args) {
        if (s == null) {
            return false;
        }
        if (args.length < 1) {
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            if (args[i].shortValue() == s.shortValue()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEmpty(String s) {
        return ((s == null) || s.isEmpty());
    }

    public static boolean isEmpty(List list) {
        return ((list == null) || list.isEmpty());
    }

    public static boolean isNull(Short value) {

        if (value == null) {
            return true;
        }

        if (value <= 0) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Float value) {

        if (value == null) {
            return true;
        }

//        if (value <= 0) {
//            return true;
//        }

        return false;
    }

    public static boolean isNull(BigInteger value) {

        if (value == null) {
            return true;
        }

//        if (value.toString().equals("0")) {
//            return true;
//        }

        return false;
    }

    public static boolean isNull(Long value) {
        if (value == null) {
            return true;
        }

        return false;
    }

    public static boolean isNull(byte[] byteData) {
        if (byteData == null || byteData.length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Integer integer) {
        if (integer == null) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNull(BigDecimal number) {
        if (number == null) {
            return true;
        }
        return false;
    }

    public static Long getLong(Object s) {
        if (s == null || s.toString().length() < 1) {
            return null;
        }

        try {
            Long l = Long.parseLong(s.toString());
            return l;
        } catch (Exception e) {
            return null;
        }
    }

    public static long differenceBetweenDatesInYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        long year = Math.abs(date2.getTime() / 1000 - date1.getTime() / 1000) / (365 * 24 * 60 * 60);
        return year;
    }

    public static long differenceBetweenDatesInYear(Date date) {
        long presentDate = new Date().getTime();
        long pastDate = date.getTime();
        long year = (presentDate / 1000 - pastDate / 1000) / (365 * 24 * 60 * 60);
        return year;
    }

    /**
     * add provided days with provided date
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * add provided days with provided date
     */
    public static Date addYears(Date date, int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    /**
     * find the difference between two dates
     */
    public static boolean dateDifference(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return false;
        }

        long diffInDays = (endDate.getTime() - startDate.getTime());
        diffInDays /= (1000 * 60 * 60 * 24);
        diffInDays /= (30 * 12);
        System.out.println(diffInDays);

        if (diffInDays < 18) {
            return false;
        }
        return true;
    }

    public static int getAge(Date dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }

        Date now = new Date();

        if (dateOfBirth.after(now)) {
            return 0;
        }

        int year = (int) ((now.getTime() / 1000 - dateOfBirth.getTime() / 1000) / (60 * 60 * 24 * 365));

        return year;
    }

    public static int checkAge(Date dateOfBirth, Date date) {
        if (dateOfBirth == null) {
            return 0;
        }

        if (date == null) {
            date = new Date();
        }

        if (dateWithoutTime(dateOfBirth).after(dateWithoutTime(date))) {
            return 0;
        }

        int year = (int) ((date.getTime() / 1000 - dateOfBirth.getTime() / 1000) / (60 * 60 * 24 * 365));

        return year;
    }

    public static int getDifferenceInDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }

        int year = (int) ((date1.getTime() / 1000 - date2.getTime() / 1000) / (60 * 60 * 24));

        return year;
    }

    public static int getAbsoluteDifferenceInDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }

        int year = (int) ((date1.getTime() / 1000 - date2.getTime() / 1000) / (60 * 60 * 24));

        return Math.abs(year);
    }

    public static boolean compareLong(Long value1, Long value2) {
        if (value1 == null && value2 == null) {
            return true;
        }
        if (value1 == null || value2 == null) {
            return false;
        }
        if (value1.compareTo(value2) == 0) {
            return true;
        }
        return false;
    }

    public static String buildAgeComparQuery(String colName, String compare, Long age) {
        if (Utils.isEmpty(colName) || Utils.isEmpty(compare) || age == null || age.equals(0L)) {
            return "";
        }

        return " trunc((months_between(sysdate, " + colName + "))/12) " + compare + " " + age + " AND ";
    }

    public static String buildDateCompareQuery(String colName, String compare, Date date) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(compare) && date != null) {
            return " TRUNC(" + colName + ") >= TO_DATE('" + getDateToString(date) + "','yyyy-mm-dd') AND ";
        }

        return "";
    }

    public static String buildDatesCompareQuery(String colName, String compare, Date date) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(compare) && date != null) {
            return " TRUNC(" + colName + ") " + compare + " TO_DATE('" + getDateToString(date) + "','yyyy-mm-dd') AND ";
        }

        return "";
    }

    public static String buildEqualQuery(String colName, String value) {
        if (Utils.isEmpty(colName) || Utils.isEmpty(value)) {
            return "";
        }

        return " AND UPPER(" + colName + ") = '" + value.toUpperCase() + "' ";
    }

    public static String buildEqualQuery(String colName, Long value) {
        if (Utils.isEmpty(colName) || value == null || value.equals(0L)) {
            return "";
        }

        return " AND " + colName + " = " + value + " ";
    }

    public static String buildEqualQuery(String colName, Integer value) {
        if (Utils.isEmpty(colName) || value == null || value.equals(0)) {
            return "";
        }

        return " AND " + colName + " = " + value + " ";
    }

    public static String buildLikeQuery(String colName, String value) {
        if (!Utils.isEmpty(colName) && !Utils.isEmpty(value)) {
            value = value.replaceAll("'", "");
            return " UPPER(" + colName + ") LIKE '%" + value.toUpperCase() + "%' AND ";
        }

        return "";
    }

    public static String buildMultiValueQuery(String colName, List<String> values) {
        if (isEmpty(colName) || values == null || values.size() == 0) {
            return "";
        }

        String query = "";
        boolean flag = false;
        for (String value : values) {
            value = value.replaceAll("'", "");
            if (!Utils.isEmpty(value)) {
                if (flag) {
                    query += ",";
                }
                flag = true;
                query += "'" + value.toUpperCase() + "'";
            }
        }
        if (Utils.isEmpty(query)) {
            return "";
        }

        return " UPPER(" + colName + ") IN(" + query + ") AND ";
    }

    public static String buildCompareQuery(String colName, String compare, Long value) {
        if (Utils.isEmpty(colName) || value == null || value.equals(0L) || Utils.isEmpty(compare)) {
            return "";
        }

        return " " + colName + " " + compare + " " + value.toString() + " AND ";
    }

    public static Object compareDates(Date date1, Date date2) {
        try {
            return dateWithoutTime(date1).compareTo(dateWithoutTime(date2));
        } catch (Exception e) {
            return null;
        }
    }

    public static Object compareWithCurrentDate(Date date) {
        try {
            return dateWithoutTime(date).compareTo(dateWithoutTime(new Date()));
        } catch (Exception e) {
            return null;
        }
    }

    public static Date dateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int checkDobEligibility(Date dateOfBirth, Date date) {
        if (dateOfBirth == null) {
            return 0;
        }

        if (date == null) {
            date = new Date();
        }

        try {
            Calendar dob = Calendar.getInstance();
            dob.setTime(dateWithoutTime(dateOfBirth));
            Calendar today = Calendar.getInstance();
            today.setTime(dateWithoutTime(date));

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
                age--;
            } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
            return age;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static Timestamp getTimestampFromDate(Date date) {
        if (date == null) {
            return getCurrentTimestamp();
        }
        return new Timestamp(date.getTime());
    }

    public static boolean indexValueExists(Object[] objecta, int index) {
        if (objecta == null) {
            return false;
        }
        return objecta.length >= (index + 1) && objecta[index] != null;
    }

    public static String listToStringOfIds(List<Integer> list) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static String listToString(List<BigDecimal> list) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static String listToStringOfLongs(List<Long> list) {
        if (list == null || list.size() < 1) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static String listToStringOfCodes(List<String> list) {
        if (list == null || list.size() < 1) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            result.append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    public static Timestamp dateToTimeStamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Date getDateParam(Date date, boolean maxTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (maxTime) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        return df.format(date);
    }

    public static Integer getKeyFromMap(String val, HashMap<Integer, String> HASH_MAP) {

        if (val == null || HASH_MAP == null || HASH_MAP.size() == 0) {
            return 0;
        }

        for (Map.Entry<Integer, String> e : HASH_MAP.entrySet()) {
            if (e == null) {
                continue;
            }

            if (e.getValue().equalsIgnoreCase(val.trim())) {

                return e.getKey();
            }
        }

        return 0;
    }

    public static String buildOrderByQuery(String columnName, Integer order) {
        if (isEmpty(columnName) || order == null) {
            return "";
        }

        String orderBy = "DESC";
        if (order.intValue() > 0) {
            orderBy = "ASC";
        }

        return " ORDER BY " + columnName + " " + orderBy;
    }

    public static String buildOrderByQuery(String columnName, Integer order, boolean isOrderBy) {
        if (isEmpty(columnName) || order == null) {
            return "";
        }

        String orderBy = "DESC";
        if (order.intValue() > 0) {
            orderBy = "ASC";
        }
        if (isOrderBy) {
            return " ORDER BY (" + columnName + ") " + orderBy;
        } else {
            return columnName + " " + orderBy;
        }
    }

    public static String getBarCode(List<BigDecimal> idList, String code, String source, String destination) {
        BigDecimal id = null;
        if (idList == null || idList.size() < 0) {
            return "";
        } else {
            id = idList.get(0);
        }
        String barcode = (code == null ? "" : code);
        if (!isEmpty(source)) {
            if (source.length() < 3) {
                source = "0" + source;
            }
            barcode += source;
        }
        if (!isEmpty(destination)) {
            if (destination.length() < 3) {
                destination = "0" + destination;
            }
            barcode += destination;
        }
        Calendar calendar = Calendar.getInstance();
        int day, month, year;
        calendar.setTime(new Date());
        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        if (day < 10) {
            barcode += "0" + day;
        } else {
            barcode += day;
        }
        month += 1;
        if (month < 10) {
            barcode += "0" + month;
        } else {
            barcode += month;
        }

        barcode += year;

        barcode = padBarcode(barcode, id);

        return barcode;
    }

    private static String padBarcode(String barcode, BigDecimal id) {
        if (Utils.isNull(id)) {
            return barcode;
        }
        BigDecimal bd = id.stripTrailingZeros();
        int idLength = bd.precision() - bd.scale();
        String pad = idLength == 1 ? "00" : idLength == 2 ? "0" : "";
        barcode += pad + id;

        return barcode;
    }

    public static boolean isEmptyObjectList(List<Object> objectList) {
        if (objectList == null || objectList.size() <= 0) {
            return true;
        }

        int sizeOfList = objectList.size();
        int countEmpty = 0;

        for (Object object : objectList) {
            if (object == null) {
                countEmpty++;
            }
        }

        if (countEmpty == sizeOfList) {
            return true;
        }

        return false;
    }

    public static boolean isEmptyStringList(List<String> stringList) {
        if (stringList == null || stringList.size() <= 0) {
            return true;
        }

        int sizeOfList = stringList.size();
        int countEmpty = 0;

        for (String str : stringList) {
            if (str == null) {
                countEmpty++;
            }
        }

        if (countEmpty == sizeOfList) {
            return true;
        }

        return false;
    }

    public static boolean isEmptyLongList(List<Long> intList) {
        if (intList == null || intList.size() <= 0) {
            return true;
        }

        int sizeOfList = intList.size();
        int countEmpty = 0;

        for (Long intVal : intList) {
            if (intVal == null) {
                countEmpty++;
            }
        }

        if (countEmpty == sizeOfList) {
            return true;
        }

        return false;
    }

    public static Object[] buildInQuery(String columnName, List<Object> valueList, List<Object> objectList) {
        if (columnName == null || isEmptyObjectList(valueList)) {
            return null;
        }
        if (objectList == null) {
            objectList = new ArrayList<Object>();
        }
        String query = " AND " + columnName + " IN (";
        for (int i = 0; i < valueList.size(); i++) {
            query += "?";
            if (i != valueList.size() - 1) {
                query += ",";
            }
            objectList.add(valueList.get(i));
        }
        query += ") ";

        Object obj[] = new Object[2];
        obj[0] = query;
        obj[1] = objectList;

        return obj;

    }

    public static Object[] buildNotInQuery(String columnName, List<Object> valueList, List<Object> objectList) {
        if (columnName == null || isEmptyObjectList(valueList)) {
            return null;
        }

        String query = " AND " + columnName + " NOT IN(";
        for (int i = 0; i < valueList.size(); i++) {
            query += "?";
            if (i != valueList.size() - 1) {
                query += ",";
            }
            objectList.add(valueList.get(i));
        }
        query += ")";

        Object obj[] = new Object[2];
        obj[0] = query;
        obj[1] = objectList;

        return obj;

    }

    public static Object[] buildInQuery(List<String> valueList, List<Object> objectList) {

        if (isEmptyStringList(valueList)) {
            return null;
        }

        String query = "";
        for (int i = 0; i < valueList.size(); i++) {
            if (!isEmpty(valueList.get(i))) {
                query += "?";
                if (i != valueList.size() - 1) {
                    query += ",";
                }
                objectList.add(valueList.get(i));
            }

        }

        Object obj[] = new Object[2];
        obj[0] = query;
        obj[1] = objectList;

        return obj;
    }

    public static Object[] buildInQueryOfLong(List<Long> valueList, List<Object> objectList) {

        if (isEmptyLongList(valueList)) {
            return null;
        }

        String query = "";
        for (int i = 0; i < valueList.size(); i++) {
            if (!isNull(valueList.get(i))) {
                query += "?";
                if (i != valueList.size() - 1) {
                    query += ",";
                }
                objectList.add(valueList.get(i));
            }

        }

        Object obj[] = new Object[2];
        obj[0] = query;
        obj[1] = objectList;

        return obj;
    }

    public static Object[] buildInQuery(List<String> valueList, List<Object> objectList, boolean isUpper) {

        if (isEmptyStringList(valueList)) {
            return null;
        }

        String query = "";
        for (int i = 0; i < valueList.size(); i++) {
            if (!isEmpty(valueList.get(i))) {
                query += "?";
                if (i != valueList.size() - 1) {
                    query += ",";
                }
                objectList.add(valueList.get(i).trim().toUpperCase());
            }

        }

        Object obj[] = new Object[2];
        obj[0] = query;
        obj[1] = objectList;

        return obj;

    }

    public static List<Object> getIdListInt(List<Integer> values) {
        if (values == null || values.size() <= 0) {
            return null;
        }

        List<Object> valueList = new ArrayList<Object>();
        for (Integer id : values) {
            if (isNull(id)) {
                continue;
            }
            valueList.add(id);
        }
        return valueList;
    }

    public static List<Object> getIdList(List<Long> values) {
        if (values == null || values.size() <= 0) {
            return null;
        }

        List<Object> valueList = new ArrayList<Object>();
        for (Long id : values) {
            if (isNull(id)) {
                continue;
            }
            valueList.add(id);
        }
        return valueList;
    }

    public static List<Long> getIdList(List<BigDecimal> values, boolean isLong) {
        if (values == null || values.size() <= 0) {
            return null;
        }

        List<Long> valueList = new ArrayList<Long>();
        for (BigDecimal id : values) {
            if (isNull(id)) {
                continue;
            }
            valueList.add(id.longValue());
        }
        return valueList;
    }

    public static String toUpper(String value, boolean isTrim) {
        if (isEmpty(value)) {
            return null;
        }

        if (isTrim) {
            return value.trim().toUpperCase();
        }

        return value.toUpperCase();
    }

    public static String convertIdNameMapToSqlString(HashMap<Long, String> map, String column) {

        if (Utils.isEmpty(column) || map == null || map.size() <= 0) {
            return null;
        }

        String str = "";
        try {
            for (Long key : map.keySet()) {
                if (isNull(key) || isEmpty(map.get(key))) {
                    continue;
                }

                str += " when " + column + " = " + key + " then '" + map.get(key) + "' ";
            }
        } catch (Throwable t) {
            System.err.print(t);
            return null;
        }
        if (!Utils.isEmpty(str)) {
            return " case " + str + " end ";
        }

        return null;
    }

    /**
     * if anyone is null, then not equal.
     *
     * @param first
     * @param second
     * @return
     */
    public static boolean isEqual(String first, String second, boolean ignoreCase) {
        if (isEmpty(first) && isEmpty(second)) {
            return true;
        }
        if (isEmpty(first) || isEmpty(second)) {
            return false;
        }
        if (ignoreCase) {
            return first.equalsIgnoreCase(second);
        }
        return first.equals(second);
    }

    public static boolean isEqual(Integer first, Integer second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return (first.intValue() == second.intValue());
    }

    public static boolean isEqual(byte[] first, byte[] second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return isEqual(md5(first), md5(second), false);
    }

    public static String md5(byte[] arr) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(arr);
            String md5Str = new BigInteger(digest.digest()).toString(16);
            return md5Str;
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean isEqual(Long first, Long second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return (first.intValue() == second.intValue());
    }

    public static boolean isEqual(Short first, Short second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return (first.shortValue() == second.shortValue());
    }

    public static boolean isEqual(BigDecimal first, BigDecimal second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return (first.compareTo(second) == 0 ? true : false);
    }

    public static boolean isEqual(BigInteger first, BigInteger second) {
        if (first == null && second == null) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }

        return (first.compareTo(second) == 0 ? true : false);
    }

    public static Integer getInteger(Long value) {
        if (value == null) {
            return null;
        }
        try {
            return value.intValue();
        } catch (Exception e) {

        }

        return 0;
    }

    public static boolean isStringListEmpty(List<String> list) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        for (String string : list) {
            if (!isEmpty(string)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLongListEmpty(List<Long> list) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        for (Long l : list) {
            if (l != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBigDecimalListEmpty(List<Long> list) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        for (Long l : list) {
            if (l != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isObjectListEmpty(List<Object> list) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        for (Object l : list) {
            if (l != null) {
                return false;
            }
        }
        return true;
    }

    public static List<String> getListFromString(String value) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(value)) {
            return list;
        }

        StringTokenizer st = new StringTokenizer(value, ",");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!isEmpty(str)) {
                list.add(str);
            }
        }

        return list;
    }

    public static Long getLongFromObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            return  ((BigDecimal) object).longValue();
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        if (object instanceof Integer) {
            return Long.valueOf((Integer) object);
        }
        if (object instanceof BigInteger) {
            return ((BigInteger) object).longValue();
        }
        if (object instanceof String) {
            try {
                return Long.parseLong((String) object);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    public static Double getDoubleFromObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            return  ((BigDecimal) object).doubleValue();
        }
        if (object instanceof Long) {
            return ((Long) object).doubleValue();
        }
        if (object instanceof Double) {
            return (Double) object;
        }
        if (object instanceof Integer) {
            return Double.valueOf((Integer) object);
        }
        if (object instanceof BigInteger) {
            return ((BigInteger) object).doubleValue();
        }
        if (object instanceof String) {
            try {
                return Double.parseDouble((String) object);
            } catch (Throwable t) {

            }
        }
        return null;
    }

    public static String getMobileNumberFormat(Integer mobile) {
        if (Utils.isNull(mobile)) {
            return "";
        }

        return String.format("%011d", mobile);
    }

    //    public static boolean isInList(int num, IntStream intStream) {
//        if (intStream == null || intStream.count() <= 0L) {
//            return false;
//        }
//        OptionalInt result = intStream.filter(value -> value == num).findFirst();
//        if (result.isPresent()) {
//            return true;
//        }
//
//        return false;
//    }
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    public static List<Long> uniqueIntegerList(List<Long> list) {
        if (list == null) {
            return new ArrayList<Long>();
        }

        HashSet<Long> hashSet = new HashSet<Long>();
        hashSet.addAll(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

    public static String[] generateKey(int numberOfKey, boolean isIncludeSign) {
        String[] keys = new String[numberOfKey];
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            for (int key = 0; key < numberOfKey; key++) {
                String license = "";
                String randomNum = Integer.valueOf(prng.nextInt()).toString();
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                byte[] result = sha.digest(randomNum.getBytes());
                StringBuilder resultbuilder = new StringBuilder();

                char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                for (int idx = 0; idx < result.length; ++idx) {
                    byte b = result[idx];
                    resultbuilder.append(digits[(b & 0xf0) >> 4]);
                    resultbuilder.append(digits[b & 0x0f]);
                }

                if (isIncludeSign) {
                    if (resultbuilder.length() > 0) {
                        for (int i = 0; i < resultbuilder.length(); i++) {
                            license += resultbuilder.charAt(i);
                            if (i != 0 && i % 10 == 0) {
                                license += "-";
                            }
                        }
                        license = license.toUpperCase();
                    }
                } else {
                    license = resultbuilder.toString().toUpperCase();
                }
                keys[key] = license;
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            keys = null;
        }
        return keys;
    }

    public static boolean isEntity(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        ret.add(Date.class);

        return ret;
    }

    /*
        public static String prettyPrintJsonString(String string) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(string);
            String prettyJsonString = gson.toJson(je);

            return prettyJsonString;
        }
     */
    public static Object getInstance(Class<?> clazz) {
        try {
            //Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor();
            Object object = ctor.newInstance(new Object[]{});

            return object;
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        return null;
    }

    public static Class<?> getGenericClassFromCollection(Field field) {
        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> clazz = (Class<?>) listType.getActualTypeArguments()[0];

        return clazz;
    }

    public static String getJSON(Object o) throws Throwable {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(o);
        } catch (Throwable e) {
            throw e;
        }
    }

    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static String dateToString(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        } catch (Throwable t) {
            System.err.println(t.getMessage());
            return null;
        }
    }

    public static byte[] getObjectToByte(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object getByteToObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static Integer getCurrentUserFromContext(Object context) {
        return 1;
    }

    public static int getListSize(List list) {
        if (Utils.isEmpty(list)) {
            return 0;
        }

        return list.size();
    }

    public static ServiceError throwableError(Throwable t) {
        return new ServiceError(ErrorCode.SERVICE_ERROR.ordinal(), t != null && t.getMessage() != null
                ? t.getMessage() : "Please contact with system administrator for further information about error");
    }

    public static boolean isValidDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists() || !file.isDirectory()) {
            return false;
        }

        return true;
    }

    public static String escapeString(String value) {
        if (Utils.isEmpty(value)) {
            return value;
        }
        value = StringEscapeUtils.escapeJava(value);
//        value = StringEscapeUtils.escapeHtml3(value);
//        value = StringEscapeUtils.escapeEcmaScript(value);
        return value;
    }

    public static boolean isValidFiscalYear(String fiscalYear) {
        boolean valid = false;
        if (Utils.isEmpty(fiscalYear)) {
            return valid;
        }
        if (fiscalYear.length() != 7) {
            return valid;
        }
        String pattern = "(\\d{4})-(\\d{2})";
        try {
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(fiscalYear.trim());
            if (m.find()) {
                valid = true;
                String[] arr = fiscalYear.split("-");
                if (arr[0].length() != 4 && arr[1].length() != 2) {
                    valid = false;
                } else {
                    String left = arr[0].substring(arr[0].length() - 2);
                    String right = arr[1];
                    Integer leftYear = Integer.parseInt(left);
                    Integer rightYear = Integer.parseInt(right);
                    if (leftYear.intValue() == 99) {
                        if (rightYear.intValue() != 0) {
                            valid = false;
                        }
                    } else {
                        int result = rightYear.intValue() - leftYear.intValue();
                        if (result != 1) {
                            valid = false;
                        }
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            valid = false;
        }
        return valid;
    }

//    public static String splitCamelCase(String s, boolean returnUpperCamelCase) {
//        if (Utils.isEmpty(s)) {
//            return "";
//        }
//        if (returnUpperCamelCase) {
//            s = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, s);
//        }
//        return s.replaceAll(
//                String.format("%s|%s|%s",
//                        "(?<=[A-Z])(?=[A-Z][a-z])",
//                        "(?<=[^A-Z])(?=[A-Z])",
//                        "(?<=[A-Za-z])(?=[^A-Za-z])"
//                ),
//                " "
//        );
//    }

    public static boolean isEqualObjects(Object o, Object o1, Class<?> type) {
        if (o == null && o1 == null) {
            return true;
        }
        if (o == null || o1 == null) {
            return false;
        }
        if (type.equals(Date.class)) {
            return isDateEqual((Date) o, (Date) o1);
        } else if (type == byte[].class) {
            return isEqual((byte[]) o, (byte[]) o1);
        } else {
            return isEqual(String.valueOf(o), String.valueOf(o1), true);
        }
    }

    public static Date getDateFromGregorian(XMLGregorianCalendar calendar) {
        try {
            return calendar.toGregorianCalendar().getTime();
        } catch (Exception ex) {
        }
        return null;
    }

    public static XMLGregorianCalendar getGregorianFromDate(Date date) {
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlDate;
    }

    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth() {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.MONTH);
        return year;
    }

    public static String getCurrentFiscalYear() {
        String fiscalString="";
        int currentMonth=getCurrentMonth();
        int currentYear=getCurrentYear();
        int currentYear2Digit=currentYear-2000;


        if(currentMonth<7)
            fiscalString=(currentYear-1)+"-"+(currentYear-2000);
        else
            fiscalString=currentYear+"-"+(currentYear+1-2000);

        System.out.println("current fiscal year  ======="+fiscalString);

        return fiscalString;
    }

    public static String getCurrentAndPreviousFiscalYearForInQuery() {
        int currentYear=getCurrentYear();
        int currentYear2Digit=currentYear-2000;
        int currentMonth=getCurrentMonth();

        String str="";

        if(currentMonth<7)
        {
            str+="'"+(currentYear-2)+"-"+(currentYear2Digit-1)+"'";
            str+=",'"+(currentYear-1)+"-"+(currentYear2Digit)+"'";
        }
        else
        {
            str+="'"+(currentYear-1)+"-"+(currentYear2Digit)+"'";
            str+=",'"+(currentYear)+"-"+(currentYear2Digit+1)+"'";
        }

        System.out.println("fiscal range  ======="+str);
        return str;
    }

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return "Error Message Not Found";
        }
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String getDOBFromAge(Integer age, String pattern) {
        if (Utils.isNull(age) || Utils.isNull(pattern)) {
            return null;
        }

        LocalDate now = LocalDate.now();
        LocalDate dob = now.minusYears(age);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return dob.format(formatter);

        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static boolean isBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0") || value.equalsIgnoreCase("1");
    }

    public static boolean getBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
    }

    public static Long getUserIdFromHeader(HttpServletRequest request) throws Exception {
        String userId = request.getHeader(Defs.HEADER.X_USER_ID);
        return Long.parseLong(userId);
    }
}

