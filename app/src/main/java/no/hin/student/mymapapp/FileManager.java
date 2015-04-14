package no.hin.student.mymapapp;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Aleksander on 14.04.2015.
 */
public class FileManager
{
    private Context context;
    public static final String FILE_NAME = "mapPositions";

    public FileManager(Context context)
    {
        this.context = context;
    }

    public void savePosition(LatLng latLong)
    {
        SerializableLatLng point = new SerializableLatLng(latLong.latitude, latLong.longitude);
        ObjectOutputStream objectOutputStream = getObjectOutputStream();

        if (objectOutputStream != null)
            writeObjectAndCloseStream(point, objectOutputStream);
        else
            Log.d("Error: ", "Couldn't open outputstream in FileManager.savePosition");
    }

    private ObjectOutputStream getObjectOutputStream()
    {
        ObjectOutputStream objectOutputStream = null;

        try
        {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            objectOutputStream = new ObjectOutputStream(fos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return objectOutputStream;
    }

    private void writeObjectAndCloseStream(SerializableLatLng point, ObjectOutputStream os)
    {
        try
        {
            os.writeObject(point);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                os.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private class SerializableLatLng
    {
        public double latitude;
        public double longtitude;

        public SerializableLatLng(double latitude, double longtitude)
        {
            this.latitude = latitude;
            this.longtitude = longtitude;
        }
    }
}
