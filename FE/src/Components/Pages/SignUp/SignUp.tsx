import {
  makeStyles,
  Container,
  Typography,
  TextField,
  Button,
} from '@material-ui/core';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useState } from 'react';
import styled from '@emotion/styled';

interface IFormInput {
  id: string;
  name: string;
  password: string;
  passwordConfirm: string;
  authkey: string;
}

const schema = yup.object().shape({
  id: yup
    .string()
    .required('아이디는 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.')
    .matches(/^[a-zA-Z0-9]*$/, '영문과 숫자만 입력 가능합니다.'),
  name: yup
    .string()
    .required('이름은 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.'),
  password: yup
    .string()
    .required('비밀번호는 필수 입니다.')
    .min(8, '8자 이상 입력하세요.')
    .max(30, '30자 이하로 입력하세요.')
    .matches(
      /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,16}$/,
      '비밀번호는 반드시 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.',
    ),
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref('password'), null], '비밀번호가 일치하지 않습니다.'),
  authkey: yup.string().required('부여받은 인증키를 입력하세요'),
});

const useStyles = makeStyles((theme) => ({
  heading: {
    textAlign: 'center',
    margin: theme.spacing(1, 0, 4),
  },
  submitButton: {
    marginTop: theme.spacing(4),
  },
}));

function SignUp() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<IFormInput>({
    resolver: yupResolver(schema),
  });

  const { heading, submitButton } = useStyles();

  const [json, setJson] = useState<string>();

  const onSubmit = (data: IFormInput) => {
    setJson(JSON.stringify(data));
  };

  return (
    <Container maxWidth="xs">
      <ContainerDiv>
        <Typography className={heading} variant="h3">
          회원가입
        </Typography>
        <form onSubmit={handleSubmit(onSubmit)} noValidate>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('id')}
            variant="outlined"
            margin="normal"
            label="ID"
            helperText={errors.id?.message}
            error={!!errors.id?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('password')}
            variant="outlined"
            margin="normal"
            label="비밀번호"
            helperText={errors.password?.message}
            error={!!errors.password?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('passwordConfirm')}
            variant="outlined"
            margin="normal"
            label="비밀번호 확인"
            helperText={errors.passwordConfirm?.message}
            error={!!errors.passwordConfirm?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('name')}
            variant="outlined"
            margin="normal"
            label="이름"
            helperText={errors.name?.message}
            error={!!errors.name?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('authkey')}
            variant="outlined"
            margin="normal"
            label="인증키"
            helperText={errors.authkey?.message}
            error={!!errors.authkey?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={submitButton}
          >
            가입하기
          </Button>
          {json && (
            <>
              <Typography variant="body1">json 데이터 출력</Typography>
              <Typography variant="body2">{json}</Typography>
            </>
          )}
        </form>
      </ContainerDiv>
    </Container>
  );
}

export default SignUp;

const ContainerDiv = styled.div`
  width: 400px;
  margin-top: 32px;
`;
