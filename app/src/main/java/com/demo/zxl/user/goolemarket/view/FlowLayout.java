package com.demo.zxl.user.goolemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup{
	public static final int verticalSpacing = UIUitls.dip2px(15);
	public static final int horizontalSpacing = UIUitls.dip2px(10);
	public List<Line> lineList = new ArrayList<Line>();
	
	public FlowLayout(Context context) {
		this(context,null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context,attrs,-1);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int left = getPaddingLeft();
		int top = getPaddingTop();
		
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);
			line.layout(left, top);
			top += line.lineHeight+verticalSpacing;
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//创建集合添加行对象
		lineList.clear();
		//创建行对象
		Line line = new Line();
		//获取宽度大小
		int width = MeasureSpec.getSize(widthMeasureSpec);
		//获取行的可用宽度
		int usedWidth = width-getPaddingLeft()-getPaddingRight();
		
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			//系统测量一次
			childView.measure(0, 0);
			//测量到的孩子节点的宽度
			int measuredWidth = childView.getMeasuredWidth();
			
			if(line.getLineViewCount()==0){
				line.addLineView(childView);
			}else if(measuredWidth+line.lineWidth+horizontalSpacing>usedWidth){
				//如果添加当前控件后,宽度大于可用宽度,则需要换行
				lineList.add(line);
				//创建新行
				line = new Line();
				line.addLineView(childView);
			}else{
				line.addLineView(childView);
			}
			//在结合中添加行对象
			if(i == getChildCount()-1){
				lineList.add(line);
			}
		}
		
		int height = getPaddingBottom()+getPaddingTop();
		for(int i=0;i<lineList.size();i++){
			height += lineList.get(i).lineHeight;
		}
		
		height += (lineList.size()-1)*verticalSpacing;
		
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	public class Line{
		//定义行宽度
		public int lineWidth;
		//定义行高度
		public int lineHeight;
		//放置view的集合
		public List<View> lineView = new ArrayList<View>();
		
		public int getLineViewCount(){
			return lineView.size();
		}
		
		public void addLineView(View view){
			lineView.add(view);
			view.measure(0, 0);
			
			lineHeight = Math.max(lineHeight, view.getMeasuredHeight());
			
			if(lineView.size() == 0){
				//节点是0个的时候,宽度就是当前控件的宽度
				lineWidth = view.getMeasuredWidth();
			}else{
				//节点大于0个的时候,宽度需要添加间距
				lineWidth += view.getMeasuredWidth()+horizontalSpacing;
			}
		}
		
		public void layout(int left,int top){
			//获取剩余宽度
			int totalSurplusWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - lineWidth;
			//剩余宽度平均分配给行对象中每一个view
			int surplusWidth = totalSurplusWidth/getLineViewCount();
			for (int i = 0; i < lineView.size(); i++) {
				//获取行中的每一个对象,给其分配宽度大小
				View view = lineView.get(i);
				//测量
				view.measure(0, 0);
				//添加分配后的宽度
				int childWidth = view.getMeasuredWidth()+surplusWidth;
				
				int width32 = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
				int height32 = MeasureSpec.makeMeasureSpec(lineHeight, MeasureSpec.EXACTLY);
				
				view.measure(width32, height32);
				
				//行中控件排列位置
				view.layout(left, top, left+childWidth, top+lineHeight);
				left += childWidth+horizontalSpacing; 
			}
		}
	}
}
