package Utils;

import GUI.MainFrame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

public class Downloads {
    private static Downloads singleton;
    private ArrayList<Download> activeDownloads;
    private ArrayList<Download> completedDownloads;

    private Downloads(){
        activeDownloads = new ArrayList<>();
        completedDownloads = new ArrayList<>();
    }

    public static Downloads getInstance(){
        if(singleton == null){
            singleton = new Downloads();
        }
        return singleton;
    }

    public ArrayList<Download> getActiveDownloads() {
        return activeDownloads;
    }

    public void setActiveDownloads(ArrayList<Download> activeDownloads) {
        this.activeDownloads = activeDownloads;
    }

    public ArrayList<Download> getCompletedDownloads() {
        return completedDownloads;
    }

    public void setCompletedDownloads(ArrayList<Download> completedDownloads) {
        this.completedDownloads = completedDownloads;
    }

    public boolean newDownload(String url){
        activeDownloads.add(new Download(url));
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./incomplete.jdm"))){
            out.writeObject(activeDownloads);
        } catch (IOException e) {
            return false;
        }
        MainFrame.getInstance(null).loadDownloads();
        return true;
    }

    public boolean setCompleted(Download downloadedFile){
        completedDownloads.add(downloadedFile);
        activeDownloads.remove(downloadedFile);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./completed.jdm"))){
            out.writeObject(completedDownloads);
            return true;
        }
        catch (FileNotFoundException e) {
            return  false;
        } catch (IOException e) {
            return false;
        }
    }

    public void remove(Download download, int listNumber){
        switch (listNumber){
            case 0:
                activeDownloads.remove(download);
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./incomplete.jdm"))){
                    out.writeObject(activeDownloads);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainFrame.getInstance(null).loadDownloads();
                break;
            case 1:
                completedDownloads.remove(download);
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./completed.jdm"))){
                    out.writeObject(completedDownloads);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainFrame.getInstance(null).loadCompletedDowloads();
                break;
        }
    }
}
