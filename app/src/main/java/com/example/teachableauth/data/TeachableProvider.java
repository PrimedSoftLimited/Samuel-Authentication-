package com.example.teachableauth.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TeachableProvider extends ContentProvider {

    public android.widget.EditText firstName, lastName, username, email, password;

    public static final String LOG_TAG = TeachableProvider.class.getSimpleName();

    //URI matcher code for the content URI for the Pets Table
    private static final int TUTOR = 100;

    //URI matcher code for the content URI for a single row in Pets Table
    private static final int TUTOR_ID = 101;

    //Creates UriMatcher object (global variable)
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer: this is run the first time anything is called from this class
    static {
        sUriMatcher.addURI(TeachableContract.TeachableEntry.CONTENT_AUTHORITY, TeachableContract.PATH_TUTOR, TUTOR);
        sUriMatcher.addURI(TeachableContract.TeachableEntry.CONTENT_AUTHORITY, TeachableContract.PATH_TUTOR + "/*", TUTOR_ID);
    }

    //Database Helper Object
    private DatabaseHelper myDbHelper;

    //    /**
//     * Implement this to initialize your content provider on startup.
//     * This method is called for all registered content providers on the
//     * application main thread at application launch time.  It must not perform
//     * lengthy operations, or application startup will be delayed.
//     *
//     * <p>You should defer nontrivial initialization (such as opening,
//     * upgrading, and scanning databases) until the content provider is used
//     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
//     * keeps application startup fast, avoids unnecessary work if the provider
//     * turns out not to be needed, and stops database errors (such as a full
//     * disk) from halting application launch.
//     *
//     * <p>If you use SQLite, {@link SQLiteOpenHelper}
//     * is a helpful utility class that makes it easy to manage databases,
//     * and will automatically defer opening until first use.  If you do use
//     * SQLiteOpenHelper, make sure to avoid calling
//     * {@link SQLiteOpenHelper#getReadableDatabase} or
//     * {@link SQLiteOpenHelper#getWritableDatabase}
//     * from this method.  (Instead, override
//     * {@link SQLiteOpenHelper#onOpen} to initialize the
//     * database when it is first opened.)
//     *
//     * @return true if the provider was successfully loaded, false otherwise
//     */
    @Override
    public boolean onCreate() {
        myDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    //    /**
//     * Implement this to handle query requests from clients.
//     *
//     * <p>Apps targeting {@link Build.VERSION_CODES#O} or higher should override
//     * {@link #query(Uri, String[], Bundle, CancellationSignal)} and provide a stub
//     * implementation of this method.
//     *
//     * <p>This method can be called from multiple threads, as described in
//     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
//     * and Threads</a>.
//     * <p>
//     * Example client call:<p>
//     * <pre>// Request a specific record.
//     * Cursor managedCursor = managedQuery(
//     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
//     * projection,    // Which columns to return.
//     * null,          // WHERE clause.
//     * null,          // WHERE clause value substitution
//     * People.NAME + " ASC");   // Sort order.</pre>
//     * Example implementation:<p>
//     * <pre>// SQLiteQueryBuilder is a helper class that creates the
//     * // proper SQL syntax for us.
//     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
//     *
//     * // Set the table we're querying.
//     * qBuilder.setTables(DATABASE_TABLE_NAME);
//     *
//     * // If the query ends in a specific record number, we're
//     * // being asked for a specific record, so set the
//     * // WHERE clause in our query.
//     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
//     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
//     * }
//     *
//     * // Make the query.
//     * Cursor c = qBuilder.query(mDb,
//     * projection,
//     * selection,
//     * selectionArgs,
//     * groupBy,
//     * having,
//     * sortOrder);
//     * c.setNotificationUri(getContext().getContentResolver(), uri);
//     * return c;</pre>
//     *
//     * @param uri           The URI to query. This will be the full URI sent by the client;
//     *                      if the client is requesting a specific record, the URI will end in a record number
//     *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
//     *                      that _id value.
//     * @param projection    The list of columns to put into the cursor. If
//     *                      {@code null} all columns are included.
//     * @param selection     A selection criteria to apply when filtering rows.
//     *                      If {@code null} then all rows are included.
//     * @param selectionArgs You may include ?s in selection, which will be replaced by
//     *                      the values from selectionArgs, in order that they appear in the selection.
//     *                      The values will be bound as Strings.
//     * @param sortOrder     How the rows in the cursor should be sorted.
//     *                      If {@code null} then the provider is free to define the sort order.
//     * @return a Cursor or {@code null}.
//     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase database = myDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case TUTOR:
//                cursor = database.query(TeachableContract.TeachableEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
//                break;
            case TUTOR_ID:
//                checkUser(TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD);
                String id = uri.getPathSegments().get(1);
                String[] mProjection = {TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD};
                String mSelection = TeachableContract.TeachableEntry.COLUMN_EMAIL + "m?" + " and " + TeachableContract.TeachableEntry.COLUMN_PASSWORD + "m?";
                String[] mSelectionArgs = new String[]{id};

                cursor = database.query(TeachableContract.TeachableEntry.TABLE_NAME, mProjection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

//    public boolean checkUser(String Email, String Password){
//        Cursor cursor;
//        String[] projection = {TeachableContract.TeachableEntry.UID, TeachableContract.TeachableEntry.COLUMN_EMAIL, TeachableContract.TeachableEntry.COLUMN_PASSWORD};
//
//        SQLiteDatabase database = myDbHelper.getReadableDatabase();
//
//        String selection = TeachableContract.TeachableEntry.COLUMN_EMAIL + "m?" + " and " + TeachableContract.TeachableEntry.COLUMN_PASSWORD + "m?";
//        String[] selectionArgs = { Email, Password };
//        cursor = database.query(TeachableContract.TeachableEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, null);
//
//        int count = cursor.getCount();
//
//        cursor.close();
//        database.close();
//
//        if (count > 0){
//            return true;
//        } else {
//            return false;
//        }
//    }

    //    /**
//     * Implement this to handle requests to insert a new row.
//     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
//     * after inserting.
//     * This method can be called from multiple threads, as described in
//     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
//     * and Threads</a>.
//     *
//     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
//     * @param values A set of column_name/value pairs to add to the database.
//     *               This must not be {@code null}.
//     * @return The URI for the newly inserted item.
//     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = sUriMatcher.match(uri);

        switch (match){
            case TUTOR:
                return insertTutor(uri, values);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    private Uri insertTutor(Uri uri, ContentValues values){
        final SQLiteDatabase database = myDbHelper.getWritableDatabase();
//        uri = TeachableContract.TeachableEntry.CONTENT_URI;
        Uri returnUri;

        long id = database.insert(TeachableContract.TeachableEntry.TABLE_NAME, null, values);
        if (id > 0){
            returnUri = ContentUris.withAppendedId(TeachableContract.TeachableEntry.CONTENT_URI, id);
            database.close();
            return returnUri;
        } else {
            throw new android.database.SQLException("Failed to insert row into: " + uri);
        }
    }

    //    /**
//     * Implement this to handle requests for the MIME type of the data at the
//     * given URI.  The returned MIME type should start with
//     * <code>vnd.android.cursor.item</code> for a single record,
//     * or <code>vnd.android.cursor.dir/</code> for multiple items.
//     * This method can be called from multiple threads, as described in
//     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
//     * and Threads</a>.
//     *
//     * <p>Note that there are no permissions needed for an application to
//     * access this information; if your content provider requires read and/or
//     * write permissions, or is not exported, all applications can still call
//     * this method regardless of their access permissions.  This allows them
//     * to retrieve the MIME type for a URI when dispatching intents.
//     *
//     * @param uri the URI to query.
//     * @return a MIME type string, or {@code null} if there is no type.
//     */
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //    /**
//     * Implement this to handle requests to update one or more rows.
//     * The implementation should update all rows matching the selection
//     * to set the columns according to the provided values map.
//     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
//     * after updating.
//     * This method can be called from multiple threads, as described in
//     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
//     * and Threads</a>.
//     *
//     * @param uri           The URI to query. This can potentially have a record ID if this
//     *                      is an update request for a specific record.
//     * @param values        A set of column_name/value pairs to update in the database.
//     *                      This must not be {@code null}.
//     * @param selection     An optional filter to match rows to update.
//     * @param selectionArgs
//     * @return the number of rows affected.
//     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
    //
//    /**
//     * Implement this to handle requests to delete one or more rows.
//     * The implementation should apply the selection clause when performing
//     * deletion, allowing the operation to affect multiple rows in a directory.
//     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
//     * after deleting.
//     * This method can be called from multiple threads, as described in
//     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
//     * and Threads</a>.
//     *
//     * <p>The implementation is responsible for parsing out a row ID at the end
//     * of the URI, if a specific row is being deleted. That is, the client would
//     * pass in <code>content://contacts/people/22</code> and the implementation is
//     * responsible for parsing the record number (22) when creating a SQL statement.
//     *
//     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
//     * @param selection     An optional restriction to apply to rows when deleting.
//     * @param selectionArgs
//     * @return The number of rows affected.
//     * @throws SQLException
//     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
