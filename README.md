## RecyclerView万能的Adapter
> 如果使用了Data Binding Library框架,并使用了RecyclerView.可以使用这个Adapter  
不用在为每一个不同的布局写单独的Adapter

## 功能
- 添加HeaderView和FooterView
- Item的Click事件处理
- 也可以添加额外的逻辑处理

## 使用
### RecyclerView.setAdapter

**item.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout>

    <data>
        <import type="android.view.View"/>
        <variable
            name="yourVariable"
            type="yourpacket.mode.XXX"/>

    </data>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"        
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        >
        <TextView
          .....................
          android:text="@{yourVariable.xxx}"
          .....................
          >
    </LinearLayout>

</layout>
```

```java

DataBindingRecyclerViewAdapter adapter = new DataBindingRecyclerViewAdapter
(mContext,R.layout.item,BR.yourVariable,variableList);
mRecyclerView.setAdapter(adapter);

```

### setOnItemClick
```java
adapter.setOnItemCkickListener(new DataBindingRecyclerViewAdapter.ItemClickListener(){
                        @Override
                        public void itemClick(View view, int position) {
                            //..................
                        }
                      });
```

### addHeader And addFooter
```java
mRecyclerView.setAdapter(adapter);
.....
LayoutInflater inflater = LayoutInflater.from(getContext());
View footerView = inflater.inflate(R.layout.recycler_view_load_more,binding.recyclerView,false);
adapter.addFooterView(footerView);
adapter.notifyDataSetChanged();
```

```java
LayoutInflater inflater = LayoutInflater.from(getContext());
View footerView = inflater.inflate(R.layout.recycler_view_load_more,binding.recyclerView,false);
adapter.addFooterView(footerView);
mRecyclerView.setAdapter(adapter);
```

### 额外的逻辑处理
如果只给item添加一个variable还不够的话可以另外添加
```java
adapter.setOnBindingViewHolderListener(new DataBindingRecyclerViewAdapter.OnBindingViewHolderListener() {
                            @Override
                            public void onHolderBinding(DataBindingRecyclerViewAdapter.DataBindingViewHolder holder, int position) {
                                ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
                                binding.setVariable(BR.presenter,new MyPresenter());

                            }
                        });
```
**item.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout>

    <data>
        <import type="android.view.View"/>
        <variable
            name="yourVariable"
            type="yourpacket.mode.XXX"/>
        <variable
              name="presenter"
              type="yourpacket.MyPresenter"/>
    </data>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"        
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        >
        <TextView
          .....................
          android:text="@{yourVariable.xxx}"
          .....................
          >
          <Button
              android:id="@+id/txtEdit"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"              
              android:visibility="@{userCode.equals(story.createUCode)?View.VISIBLE:View.GONE}"
              android:onClick="@{(view)->presenter.edit(view,yourVariable)}"
              android:text="EDIT"/>       
          <Button
            android:id="@+id/txtDelete"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"            
            android:visibility="@{userCode.equals(story.createUCode)?View.VISIBLE:View.GONE}"
            android:onClick="@{(view) -> presenter.delete(view,yourVariable)}"
            android:text="DELETE"/>

    </LinearLayout>

</layout>
```
