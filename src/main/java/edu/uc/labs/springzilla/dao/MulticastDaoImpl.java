package edu.uc.labs.springzilla.dao;

import com.typesafe.config.Config;
import edu.uc.labs.springzilla.models.MulticastSettings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MulticastDaoImpl implements MulticastDao {

    public MulticastDaoImpl(Config config) {
        this.config = config;
    }

    @Override
    public void updateSettings(MulticastSettings settings) throws IOException {
        File drblOcs = new File(config.getString("file.multicastsettings"));
        BufferedReader reader = new BufferedReader(new FileReader(drblOcs));
        String line = "";
        String newoutput = "";
        while ((line = reader.readLine()) != null) {
            if (line.matches(portPattern.pattern())) {
                Matcher matcher = portPattern.matcher(line);
                while (matcher.find()) {
                    line = line.replaceAll(portPattern.pattern(), portReplacement + "\"" + settings.getMulticastPort() + "\"");
                }
            } else if (line.matches(ttlPattern.pattern())) {
                Matcher matcher = ttlPattern.matcher(line);
                while (matcher.find()) {
                    //ms.setMulticastTTL(matcher.group(1));
                }
            } else if (line.matches(rdvAddress.pattern())) {
                Matcher matcher = rdvAddress.matcher(line);
                while (matcher.find()) {
                    //ms.setRdvAddress(matcher.group(1));
                }
            } else if (line.matches(senderAddress.pattern())) {
                Matcher matcher = senderAddress.matcher(line);
                while (matcher.find()) {
                    //ms.setSenderAddress(matcher.group(1));
                }
            }
            newoutput += line + "\n";
        }
        reader.close();
        drblOcs.delete();
       
        //File tmp = File.createTempFile("drbl-ocs", ".tmp");
        File tmp = new File(config.getString("file.multicastsettings"));
        //System.out.println("temp file = " + tmp.getAbsolutePath());
        FileOutputStream fop = new FileOutputStream(tmp);
        if (!tmp.exists()) {
            tmp.createNewFile();
        }
        byte[] contentInBytes = newoutput.getBytes();
        fop.write(contentInBytes);
        fop.flush();
        fop.close();      
        
        // Now replace the old file with the new file
        
        //tmp.renameTo(new File(config.getString("file.multicastsettings")));
        
    }

    @Override
    public MulticastSettings getSettings() throws IOException {
        MulticastSettings ms = new MulticastSettings();
        File file = new File(config.getString("file.multicastsettings"));
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.matches(portPattern.pattern())) {
                Matcher matcher = portPattern.matcher(line);
                while (matcher.find()) {
                    ms.setMulticastPort(matcher.group(1));
                }
            } else if (line.matches(ttlPattern.pattern())) {
                Matcher matcher = ttlPattern.matcher(line);
                while (matcher.find()) {
                    ms.setMulticastTTL(matcher.group(1));
                }
            } else if (line.matches(rdvAddress.pattern())) {
                Matcher matcher = rdvAddress.matcher(line);
                while (matcher.find()) {
                    ms.setRdvAddress(matcher.group(1));
                }
            } else if (line.matches(senderAddress.pattern())) {
                Matcher matcher = senderAddress.matcher(line);
                while (matcher.find()) {
                    ms.setSenderAddress(matcher.group(1));
                }
            }
        }
        reader.close();
        return ms;
    }
        
    private final Config config;
    private Pattern portPattern = Pattern.compile("MULTICAST_PORT=\"(\\d*)\"");
    private String portReplacement = "MULTICAST_PORT=";
    private Pattern ttlPattern = Pattern.compile("TIME_TO_LIVE_OPT=\"--ttl (\\d*)\"");
    private Pattern rdvAddress = Pattern.compile("MULTICAST_ALL_ADDR=\"([[\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+]*)\"");
    private Pattern senderAddress = Pattern.compile("udp_sender_extra_opt_default=\"--mcast-data-address ([\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+)\"", Pattern.CASE_INSENSITIVE);
}
