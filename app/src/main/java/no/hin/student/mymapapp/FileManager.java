package no.hin.student.mymapapp;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksander on 14.04.2015.
 */
public class FileManager
{
    public static final String FILE_NAME = "mapPositions";

    private Context context;
    private File positionsFile;

    public FileManager(Context context)
    {
        this.context = context;
        this.positionsFile = new File(context.getFilesDir() + "/" + FILE_NAME);
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
            boolean fileExists = positionsFile.exists();
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);

            if (fileExists)
            {
                objectOutputStream = new AppendingObjectOutputStream(fos);
                Log.d("asdfasdfasdf", "APPENDING");
            }
            else
            {
                objectOutputStream = new ObjectOutputStream(fos);
                Log.d("asdfasdfasdf", "FIRST");
            }
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
            FileInputStream fis = new FileInputStream(context.getFilesDir() + "/" + FILE_NAME);
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


    /*
    Had problems with appending objects to ObjectOutputStream. Found a solution at:
    http://stackoverflow.com/questions/1194656/appending-to-an-objectoutputstream
     */
    private class AppendingObjectOutputStream extends ObjectOutputStream
    {

        public AppendingObjectOutputStream(OutputStream out) throws IOException
        {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException
        {
            // do not write a header, but reset:
            // this line added after another question
            // showed a problem with the original
            reset();
        }

    }

}
