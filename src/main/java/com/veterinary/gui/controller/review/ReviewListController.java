package com.veterinary.gui.controller.review;

import com.veterinary.gui.controller.Controller;

public abstract class ReviewListController extends Controller {
  public void initialize() {
    loadTableColumns();
    loadDataList();
  }

  public abstract void loadTableColumns();

  public abstract void loadDataList();
}
