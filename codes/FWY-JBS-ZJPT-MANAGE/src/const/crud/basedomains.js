export const tableOption = {
	align: "center",
	index: true,
	border: true,
	group: [
		{
			prop: "productId",
			arrow: false,
			label: "所属产品",
			column: [
				{
					prop: "productId",
					span: 24,
					type: "select",
					label: "所属产品",
					props: {label: "name", value: "id"},
					dicUrl: "/productinfo/list",
					display: true,
					dicMethod: "get",
					filterable: false,
					required: true,
					rules: [{message: "所属产品必选", required: true}],
					cascaderItem: []
				}
			],
			display: true,
			collapse: true
		},{
			prop: "1602332023600_12606",
			arrow: false,
			label: "服务器域名配置",
			column: [
				{
					prop: "requestDomains",
					span: 24,
					type: "array",
					label: "请求地址",
					rules: [{message: "请求地址必须填写", required: true}],
					append: "",
					display: true,
					prepend: "",
					readonly: false,
					required: false,
					maxlength: 1000,
					placeholder: "服务域名地址，https协议，最多5个"
				},
				{
					prop: "wsRequestDomains",
					span: 24,
					type: "array",
					label: "ws地址",
					value: "",
					prepend: "",
					required: true,
					maxlength: 1000,
					placeholder: "web Socket地址，ws协议，最多5个"
				},
				{
					prop: "uploadDomains",
					span: 24,
					type: "array",
					label: "上传地址",
					rules: [{message: "上传地址必须填写", required: true}],
					display: true,
					required: true,
					maxlength: 1000,
					placeholder: "文件上传地址，https协议，最多5个"
				},
				{
					prop: "downloadDomains",
					span: 24,
					type: "array",
					label: "下载地址",
					rules: [{message: "下载地址必须填写", required: true}],
					display: true,
					required: true,
					maxlength: 1000,
					placeholder: "文件下载地址，https协议，最多5个"
				}
			],
			display: true,
			collapse: true
		},
		{
			prop: "1602332064439_9045",
			arrow: false,
			label: "业务域名配置",
			column: [
				{
					prop: "domains",
					span: 24,
					type: "array",
					label: "业务域名",
					rules: [{message: "业务域名必须填写", required: true}],
					display: true,
					required: true,
					maxlength: 1000,
					placeholder: "业务域名地址，https协议，最多5个"
				}
			],
			display: true,
			collapse: true
		}
	],
	column: [
		{
			prop: "productId",
			label: "所属产品",
			props: {label: "name", value: "id"},
			dicUrl: "/productinfo/list",
			dicMethod: "get",
			display: false
		},
		{
			prop: "requestDomains",
			label: "服务请求地址",
			overHidden:true,
			display: false
		},
		{
			prop: "wsRequestDomains",
			label: "ws地址",
			overHidden:true,
			display: false
		},
		{
			prop: "uploadDomains",
			label: "上传地址",
			overHidden:true,
			display: false
		},
		{
			prop: "downloadDomains",
			label: "下载地址",
			overHidden:true,
			display: false
		},
		{
			prop: "domains",
			label: "业务请求地址",
			overHidden:true,
			display: false
		},
	],
	gutter: 0,
	stripe: true,
	menuBtn: true,
	emptyBtn: true,
	emptyText: "清空",
	menuAlign: "center",
	submitBtn: true,
	indexLabel: "序号",
	labelWidth: 120,
	submitText: "提交",
	labelSuffix: "：",
	menuPosition: "center",
	labelPosition: "left",
	searchMenuSpan: 12
};
