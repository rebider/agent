<!-- 收款账户出款 同步清结算sql  勿删 -->

select 
balance_Ls,tran_Date,balance_Rcv_Acc,balance_Rcv_Bank,balance_Rcv_Name,balance_Rcv_Code,balance_Rcv_Type,
input_Time,merch_Name,flag,err_Desc,account_Id,merch_Id,balance_amt,synchronize_date,DATASOURCE
from a_colinfo_payment
where flag = '0'
and trunc(create_time) = trunc(sysdate)