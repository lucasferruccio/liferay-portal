/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.scheduler;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.site.cms.site.initializer.internal.scheduler.helper.CMSDraftNotificationCheckerHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Ferruccio
 */
@Component(service = SchedulerJobConfiguration.class)
public class CMSDraftNotificationSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		if (_log.isInfoEnabled()) {
			_log.info("Invoking CMSDraftNotificationSchedulerJobConfiguration");
		}

		return () -> _companyLocalService.forEachCompanyId(
			_cmsDraftNotificationCheckerHelper::checkDraftObjectEntries);
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		if (_log.isInfoEnabled()) {
			_log.info("Creating CMSDraftNotificationSchedulerJobConfiguration");
		}

		return TriggerConfiguration.createTriggerConfiguration(
			1, TimeUnit.MINUTE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMSDraftNotificationSchedulerJobConfiguration.class);

	@Reference
	private CMSDraftNotificationCheckerHelper
		_cmsDraftNotificationCheckerHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

}