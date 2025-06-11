package com.veterinary.gui.controller.manage;

import com.veterinary.gui.controller.Controller;

public abstract class ManageController<T> extends Controller {
  private T currentDataObject;

  public T getCurrentDataObject() {
    return currentDataObject;
  }

  public void initialize(T dataObject) {
    this.currentDataObject = dataObject;
  }

  protected abstract void handleUpdate();
}
