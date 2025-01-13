export const tableOption = {
	align: "center",
	index: true,
	border: true,
	column: [
		{
			prop: "productId",
			label: "所属产品",
			span: 24,
			type: "select",
			props: {label: "name", value: "id"},
			dicUrl: "/productinfo/list",
			dicMethod: "get",
			rules: [{message: "所属产品必选", required: true}],
		},
		{
			label: '服务类目',
			prop: 'cascader',
			type: "cascader",
			filterable: true,
			span: 24,
			hide: true,
			rules: [{
				required: true,
				message: "请选择服务类目",
				trigger: "blur"
			}],
			props: {label: "label", value: "value"},
			change: ({value, column}) => {
				this.$message.success('查看控制台', value, column)
				console.log('值改变')
			},
		},
		{
			prop: "firstName",
			label: "一级类目",
			display: false
		},
		{
			prop: "secondName",
			label: "二级类目",
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
