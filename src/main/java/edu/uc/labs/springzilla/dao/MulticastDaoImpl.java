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
                    line = line.replaceAll(ttlPattern.pattern(), ttlReplacement + "\"--ttl " + settings.getMulticastTTL() + "\"");
                }
            } else if (line.matches(rdvAddress.pattern())) {
                Matcher matcher = rdvAddress.matcher(line);
                while (matcher.find()) {
                    line = line.replaceAll(rdvAddress.pattern(), rdvReplacement + "\"" + settings.getRdvAddress() + "\"");
                }
            } else if (line.matches(senderAddress.pattern())) {
                Matcher matcher = senderAddress.matcher(line);
                while (matcher.find()) {
                    if (senderReplacement.equals("") || senderReplacement == null) {
                        line = line.replaceAll(senderAddress.pattern(), senderReplacementNull);
                    } else {
                        line = line.replaceAll(senderAddress.pattern(), senderReplacement + "\"--mcast-data-address " + settings.getSenderAddress() + "\"");
                    }
                }
            }
            newoutput += line + "\n";
        }
        reader.close();
        drblOcs.delete();
        
        File tmp = new File(config.getString("file.multicastsettings"));
        FileOutputStream fop = new FileOutputStream(tmp);
        if (!tmp.exists()) {
            tmp.createNewFile();
        }
        byte[] contentInBytes = newoutput.getBytes();
        fop.write(contentInBytes);
        fop.flush();
        fop.close();
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
                    ms.setMulticastPort(matcher.group(2));
                }
            } else if (line.matches(ttlPattern.pattern())) {
                Matcher matcher = ttlPattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group(3) == null) {
                        ms.setMulticastTTL(matcher.group(2));
                    } else {
                        ms.setMulticastTTL(matcher.group(3));
                    }
                }
            } else if (line.matches(rdvAddress.pattern())) {
                Matcher matcher = rdvAddress.matcher(line);
                while (matcher.find()) {
                    ms.setRdvAddress(matcher.group(2));
                }
            } else if (line.matches(senderAddress.pattern())) {
                Matcher matcher = senderAddress.matcher(line);
                while (matcher.find()) {
                    ms.setSenderAddress(matcher.group(2));
                }
            }
        }
        reader.close();
        return ms;
    }
    private final Config config;
    private Pattern portPattern = Pattern.compile("MULTICAST_PORT=\"((\\d*)|())\"");
    private String portReplacement = "MULTICAST_PORT=";
    private Pattern ttlPattern = Pattern.compile("TIME_TO_LIVE_OPT=\"((--ttl (\\d*))|())\"");
    private String ttlReplacement = "TIME_TO_LIVE_OPT=";
    private Pattern rdvAddress = Pattern.compile("MULTICAST_ALL_ADDR=\"((\\d+\\.\\d+\\.\\d+\\.\\d+)|())\"");
    private String rdvReplacement = "MULTICAST_ALL_ADDR=";
    private Pattern senderAddress = Pattern.compile("udp_sender_extra_opt_default=\"(--mcast-data-address\\s([\\d]+\\.[\\d]+\\.[\\d]+\\.[\\d]+)|())\"", Pattern.CASE_INSENSITIVE);
    private String senderReplacementNull = "udp_sender_extra_opt_default=\"()\"";
    private String senderReplacement = "udp_sender_extra_opt_default=";
}
