PicassoSuperFling
=================

A ListView and Adapter pair built on Picasso that pauses image loading when the ListView is flung to improve performance.


##Important Classes to Use for Implementation:
SuperFlingListView.java
SuperFlingBaseAdapter.java

## Implementation Notes
For the ListView, just add SuperFlingListView instead of ListView.

For the Adapter, create a custom adapter that extends SuperFlingBaseAdapter. In getView(...), call processImageView(...) to load the appropriate image into the specified ImageView depending on the state of the ListView.

```java
public class CustomAdapter extends SuperFlingBaseAdapter {
  ...
  @Override
  public View getView(int position, View view, ViewGroup parent) {
    ...
    // Configure the normal request that will display when the image loads
    RequestCreator normalRequest = Picasso.with(context)
              .load(holder.url)
              .placeholder(R.drawable.placeholder);

    // Call the SuperFlingBaseAdapter to decide which image to load
    processImageView(view, normalRequest, R.drawable.placeholder, holder.image);
    ...
    return view;
}
```
