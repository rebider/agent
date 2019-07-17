<!-- 物联网卡续费轧差同步清结算sql  每天0：00 勿删 -->

select flow_id,renew_id,renew_detail_id,iccid_num,agent_id,agent_name,mer_id,
  mer_name,offset_amt,already_offset_amt,c_time,c_user,u_user,status,version,CLEAN_STATUS
  from o_internet_renew_offset
where status =1 and offset_amt!=already_offset_amt
and trunc(c_time) = trunc(sysdate-1)


<!-- 物联网卡续费已轧差金额清结算同步到代理商  每天0：30 勿删 -->
select
flow_id,already_offset_amt,CLEAN_STATUS
from o_Internet_renew_offset
where status=1 and CLEAN_STATUS!=0
and PROCESS_DATE = trunc(to_char(sysdate,'YYYYMMDD')-1)
