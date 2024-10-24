package com.pranay;

import com.pranay.Fields.*;
import com.pranay.exceptions.InvalidExpressionException;

public class CronExpParserImpl implements CronExpParser {

    Field[] fields;

    public CronExpParserImpl() {
        // 0 minute, 1 hour, 2 day of month, 3 month, 4 day of week
        fields = new Field[]{
                new Minute(),
                new Hour(),
                new Day(),
                new Month(),
                new DayOfWeek()};
    }

    @Override
    public String parse(String cmd) {
        String[] cmdArr = cmd.split(" ", fields.length + 1);
        if (cmdArr.length != fields.length + 1) {
            throw new InvalidExpressionException("Invalid or incomplete cron command");
        }
        StringBuilder sd = new StringBuilder();
        int idx = 0;
        for (Field f : fields) {
            sd.append(f.parse(cmdArr[idx++]));
            sd.append("\n");
        }
        sd.append("command       ").append(cmdArr[idx]);
        return sd.toString();
    }
}
