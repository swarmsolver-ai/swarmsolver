import {Component, forwardRef} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

@Component({
  selector: 'app-tags-edit',
  templateUrl: './tags-edit.component.html',
  styleUrl: './tags-edit.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TagsEditComponent),
      multi: true
    }
  ]
})
export class TagsEditComponent implements ControlValueAccessor {
  tags: string[] = [];
  newTag: string = '';

  private onChange = (tags: string[]) => {};
  private onTouched = () => {};

  writeValue(tags: string[]): void {
    if (tags) {
      this.tags = tags;
    }
  }

  registerOnChange(fn: (tags: string[]) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    // Handle the disabled state if necessary
  }

  addTag() {
    if (this.newTag.trim()) {
      this.tags.push(this.newTag.trim());
      this.newTag = '';
      this.onChange(this.tags);
    }
  }

  removeTag(index: number) {
    this.tags.splice(index, 1);
    this.onChange(this.tags);
  }

}
