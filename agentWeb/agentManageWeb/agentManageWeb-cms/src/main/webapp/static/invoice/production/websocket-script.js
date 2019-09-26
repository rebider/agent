
//对外提供标签引入库,socket.io方式
function PwyWebSocket(opt){
	this.onError = opt.onError || function(){};		
    if(opt.name != ''){	    	
    	this.name = opt.name;
    	this.url = opt.url || 'https://wss.piaozone.com';
    	this.path = opt.path || '/bill-ws/socket.io';
    	this.transports = opt.transports || ['polling', 'websocket'];
        this.onOpen = opt.onOpen || function(){};
        this.onMessage = opt.onMessage || function(msg, handerOk, handerFail){
    		handerOk({});
    	};
        
        this.onClose = opt.close || function() {};
        this.retryFlag = true;
        this.ws = null;	         
        this.init();
    }else{
    	this.onError('请指定连接的用户名');
    }
}

PwyWebSocket.prototype = {
	init: function(name){
		var self = this;
		if(typeof name !== 'undefined' && name !== this.name){
			this.name = name;
		}
		
		this.ws = io(this.url, {
	        // 实际使用中可以在这里传递参数
	        path: this.path,
	        query: {        
	          room: 'cloud',
	          userId: this.name
	        },
	        pingInterval: 6000,
			pingTimeout: 5000,
	        transports: this.transports
	  	});
		
		this.ws.on('error', function (msg, code){				
			msg = msg ||  '连接出错';
	    	self.onError(msg);
	    });
		
		this.ws.on('connect', function(){				
			self.onOpen(self.name);
			//旧版监听自己的消息
	    	self.ws.on(self.ws.id, function(res, fn) {
	    		if(res.errcode === '0000'){
	    			self.onMessage(res.data, function(resData){
	    				var errcode = resData.errcode || '0000';
	    				resData.errcode = errcode;			    			
		    			typeof fn ==='function' && fn(resData);
		    		}, function(errData){
	    				var errcode = errData.errcode || 'sendErr';
	    				errData.errcode = errcode;			    			
		    			typeof fn ==='function' && fn(errData);
		    		})	
	    		}else{
	    			typeof fn ==='function' && fn(res);
	    		}
	    	});
	    	
			//新版监听自己的消息
	    	self.ws.on(self.name, function(res, fn) {		    		
	    		if(res.errcode === '0000'){
	    			self.onMessage(res.data, function(resData){
	    				var errcode = resData.errcode || '0000';
	    				resData.errcode = errcode;			    			
		    			typeof fn ==='function' && fn(resData);
		    		}, function(errData){
	    				var errcode = errData.errcode || 'sendErr';
	    				errData.errcode = errcode;			    			
		    			typeof fn ==='function' && fn(errData);
		    		})	
	    		}else{
	    			typeof fn ==='function' && fn(res);
	    		}
	    	});
	    	
	    	
	  	});
		
		this.ws.on('disconnect', function(msg) {
			self.onClose('断开连接', msg);
	  	});
	},
	closeWebSocket: function(){},
    send: function(to, msg, errBack, cback){ //兼容旧版
    	var param = {
    		to: to,
    		msg: msg,
    		type: 'send',
    		success: cback,
    		error: errBack
    	};
    	this.sendNew(param);
    },
    sendNew: function(opt, retry){
    	var self = this;
    	var to = opt.to || '';    	
    	var msg = opt.msg || '';
    	var success = opt.success || function(){};
    	var error = opt.error || this.onError;
    	
    	if(to === ''){
    		error('请指定发送的目标用户名');
    		return false;
    	}
    	
    	if(msg === ''){
    		error('请指定需要发送的消息！');
    		return false;
    	}    	
    	
    	this.ws.emit('server', {
    		sid: self.name + '-' +(+new Date()) + '-' + Math.random(),
			async: false,
			from: self.name,
			target: to,
			msg: msg,
			timeOut: opt.timeOut || 20
		}, function(res) {			
			if(res.errcode === '0000'){
				success(res);
			}else{
				if(retry>0){
					if(res.errcode === 'connectErr'){ //由于连接断开，自动重试三次
						self.sendNew(opt, retry - 1);
					}else{
						error(res);
					}
				}else{
					error(res);
				}
			}
		});
    }
}

if(typeof module !== 'undefined' && typeof module.exports !== 'undefined'){
	module.exports = {
		PwyWebSocket: PwyWebSocket
	}
}
