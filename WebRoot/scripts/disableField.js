	function disableField(fname) {
		es=document.getElementsByName(fname);
		for (i=0;i<es.length;i++){
			//FIXME disabled情况下字段校验通不过
			//es[i].disabled='disabled';
			x=es[i];
			if (es[i].nodeName=='SELECT') {
				h=document.createElement('input');
				h.type='hidden';
				h.name=x.name;
				h.value=x.value;
				x.parentNode.appendChild(h);
				x.disabled='disabled';
				x.name='__'+x.name;
				return;
			}
			es[i].readOnly=true;
			es[i].className='gray';
		}
	}