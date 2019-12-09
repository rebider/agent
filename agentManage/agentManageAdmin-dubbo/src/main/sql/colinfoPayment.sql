<!-- 收款账户出款 同步清结算sql  勿删 -->

select 
trim(replace(replace(balance_Ls,chr(13),''),chr(10),'')) as ID,
tran_Date as TRAN_DATE,
trim(replace(replace(balance_Rcv_Bank,chr(13),''),chr(10),'')) as PAYEE_NAME , --户名
trim(replace(replace(balance_Rcv_Acc,chr(13),''),chr(10),'')) as PAYEE_ACCT , --卡号
balance_Rcv_Name as  PAYEE_BANK_NAME , --行名
trim(replace(replace(balance_Rcv_Code,chr(13),''),chr(10),'')) as  PAYEE_BANK_CODE , --行号 支行行号
--balance_Rcv_Type,
(case balance_Rcv_Type when '2' then '1' else balance_Rcv_Type end ) as  PAYEE_CARD_TYPE , --账户类型，对私0  对公2 -> 账户类型 1对公 0对私
input_Time,
merch_Name,
'00' flag,
err_Desc as REMARK , --备注
account_Id,
merch_Id,
balance_amt,
synchronize_date,
DATASOURCE,
'13' as brand,
'00' OUT_TYPE
from a_colinfo_payment
where flag = '0'
and trunc(create_time) = trunc(sysdate)