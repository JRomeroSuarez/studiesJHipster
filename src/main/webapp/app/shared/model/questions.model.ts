import dayjs from 'dayjs';
import { IAnswer } from 'app/shared/model/answer.model';
import { IForms } from 'app/shared/model/forms.model';
import { FieldType } from 'app/shared/model/enumerations/field-type.model';

export interface IQuestions {
  id?: string;
  subtitle?: string | null;
  info?: string | null;
  fieldType?: FieldType | null;
  mandatory?: boolean | null;
  variableName?: string | null;
  units?: string | null;
  conditional?: boolean | null;
  creationDate?: string | null;
  editDate?: string | null;
  actions?: string | null;
  answers?: IAnswer[] | null;
  forms?: IForms | null;
}

export const defaultValue: Readonly<IQuestions> = {
  mandatory: false,
  conditional: false,
};
