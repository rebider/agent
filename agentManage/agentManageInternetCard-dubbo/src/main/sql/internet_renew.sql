<!-- 物联网卡续费轧差同步清结算sql  每天0：30 勿删 -->

select flow_id,renew_id,renew_detail_id,iccid_num,agent_id,agent_name,mer_id,
  mer_name,offset_amt,already_offset_amt,process_time,c_time,c_user,u_user,status,version
  from o_internet_renew_offset
where status =1 and offset_amt!=already_offset_amt
and trunc(c_time) = trunc(sysdate-1)


<!-- 物联网卡续费已轧差金额清结算同步到代理商  每天下午13：00 同步sql  勿删 -->
select
flow_id,already_offset_amt
from o_Internet_renew_offset_qs
where status=1
