package com.basic.core.service;

import com.basic.core.birt.ReportParameter;

public interface IBirtService {
    public  String createPDF(ReportParameter params) throws Exception;

    public  byte[] createFile(ReportParameter params) throws Exception;
    /**
     *  打印日志
     * @param obj
     */
    public void log(Object obj);
}
