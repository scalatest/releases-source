package org.scalatest.tools
import java.awt.Component
import javax.swing._
private[tools] trait EventHolderListCellRenderer extends ListCellRenderer[EventHolder] {
  private val defaultRenderer: ListCellRenderer[EventHolder] = (new DefaultListCellRenderer()).asInstanceOf[ListCellRenderer[EventHolder]]
  protected def decorate(renderer: JLabel, value: Object, isSelected: Boolean): Component
  def getListCellRendererComponent(list: JList[_ <: EventHolder], value: EventHolder, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component = {
    val renderer: JLabel = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).asInstanceOf[JLabel]
    decorate(renderer, value, isSelected)
  }}
