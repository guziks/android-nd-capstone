package ua.com.elius.familycart.list;

import ua.com.elius.familycart.data.item.List;

public interface ListItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onChangeList(List targetList, int position);
    List getListType();
}
