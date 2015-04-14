package no.hin.student.mymapapp;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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



    private class SerializableLatLng implements Serializable
    {
        public double latitude;
        public double longtitude;

        public SerializableLatLng(double latitude, double longtitude)
        {
            this.latitude = latitude;
            this.longtitude = longtitude;
        }
    }



    public void savePosition(LatLng latLong)
    {
        SerializableLatLng point = new SerializableLatLng(latLong.latitude, latLong.longitude);
        ObjectOutputStream objectOutputStream = getObjectOutputStream();

        if (objectOutputStream != null)
            writeObjectAndCloseStream(point, objectOutputStream);
        else
            Log.d("Error: ", "Couldn't write object -- couldn't open outputstream in FileManager.savePosition");
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

    public List<LatLng> loadPositions()
    {
        ObjectInputStream objectInputStream = getObjectInputStream();
        List<LatLng> positions = null;

        if (objectInputStream != null)
        {
            positions = readObjectsAndCloseStream(objectInputStream);
        }
        else
            Log.d("Error: ", "Couldn't read objects -- couldn't open inputstream in FileManager.loadPositions");

        return positions;
    }

    private ObjectInputStream getObjectInputStream()
    {
        ObjectInputStream objectInputStream = null;

        try
        {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fis);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return objectInputStream;
    }

    private List<LatLng> readObjectsAndCloseStream(ObjectInputStream ois)
    {
        List<LatLng> positions = new ArrayList<LatLng>();

        try
        {
            while (true)    // Loop ends when we try to read past end of file
            {
                SerializableLatLng serializableLatLng = (SerializableLatLng)ois.readObject();
                LatLng latLng = new LatLng(serializableLatLng.latitude, serializableLatLng.longtitude);
                positions.add(latLng);
            }
        }
        catch (EOFException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                ois.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        return positions;
    }

}
