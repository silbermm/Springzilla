package edu.uc.labs.springzilla.dao;

import edu.uc.labs.springzilla.json.OcsManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;


public class StatusDaoImpl implements StatusDao {

    private Pattern ocsPattern = Pattern.compile("(\\d+)\\s+(\\d*)-?(\\d\\d:\\d\\d:\\d\\d | \\d\\d:\\d\\d)\\s(.*ocsmgrd)$", Pattern.CASE_INSENSITIVE);
    private static final Logger log = Logger.getLogger(StatusDaoImpl.class);
    
    @Override
    public OcsManager getOcsStatus() throws IOException {
        OcsManager s = new OcsManager();

        String line;
        String pid = null, days = null, time = null, bin = null;
        Process p = Runtime.getRuntime().exec("ps -eo pid,etime,cmd");
        BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

        while ((line = input.readLine()) != null) {
            //if(line.matches(ocsPattern.pattern())){
            Matcher m = ocsPattern.matcher(line);
            while (m.find()) {
                log.debug("FOUND A MATCH!!" + m.group());
                pid = m.group(1);
                days = m.group(2);
                time = m.group(3);
                bin = m.group(4);
            }
            //}
        }
        input.close();
        if (pid == null) {
            s.setStatus("stopped");
            s.setElapsedDays(0);
            s.setElapsedTime(null);
        } else {
            s.setStatus("started");
            s.setElapsedDays(Integer.parseInt(days));
            s.setElapsedTime(time);
        }
        return s;

    }

    @Override
    public boolean startOcs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean stopOcs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
