package glacier;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;

public class ArchiveUploadHighLevel {
    public static String vaultName =  "TestVault"; // "*** provide vault name ***"
    
    //public static String archiveToUpload = "c:\\temp\\msdia80.dll"; //""*** provide name of file to upload ***";
    
    //TODO: factor it into 2 methods, uploadSingleFile(), and uploadFolder(), which calls uploadSingleFile() to each iteration
    public static String localFolderToArchive = "\\\\DNS-323\\Volume_1\\temp\\pandora backup";
    
    public static AmazonGlacierClient client;
    
    public static void main(String[] args) throws IOException {
        
        AWSCredentials credentials = new PropertiesCredentials(
                ArchiveUploadHighLevel.class.getResourceAsStream("/AwsCredentials.properties"));
        client = new AmazonGlacierClient(credentials);
        client.setEndpoint("https://glacier.us-east-1.amazonaws.com/");

        try {
            ArchiveTransferManager atm = new ArchiveTransferManager(client, credentials);
            
            File dir = new File(localFolderToArchive);
            for (File child: dir.listFiles()) {
            	//upload
	            UploadResult result = atm.upload(vaultName, "my archive " + (new Date()), child);
	            System.out.println("Archive ID: " + result.getArchiveId());         	
            }
            
            
        } catch (Exception e)
        {
            System.err.println(e);
        }
    }
}
