package com.ryx.credit.service.data;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;

/**
 * 附件维护
 */
public interface AttachmentService {
    PageInfo selectAll(Page page, Attachment attachment,AttachmentRel attachmentRel);
}
