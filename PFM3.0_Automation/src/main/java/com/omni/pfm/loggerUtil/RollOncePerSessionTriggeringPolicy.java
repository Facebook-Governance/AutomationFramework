/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.loggerUtil;

import java.io.File;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;

@NoAutoStart
public class RollOncePerSessionTriggeringPolicy<E> extends SizeAndTimeBasedFNATP<E> {
	private static boolean policyStarted;

	@Override
    public boolean isTriggeringEvent(File activeFile, E event) {
        if (!policyStarted) {
            policyStarted = true;
            if (activeFile.exists() && activeFile.length() > 0) {
                nextCheck = 0L;
                return true;
            }
        }
        return super.isTriggeringEvent(activeFile, event);
    }
	
	
}
