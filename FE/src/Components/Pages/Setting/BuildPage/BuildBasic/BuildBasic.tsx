import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import BuildProperty from '../BuildProperty/BuildProperty';

interface Version {
  version: string[];
}
interface Types {
  version: string[];
}

interface buildProps {
  index: number;
  Click: (value: number) => void;
}

const FramAndLib = ['SpringBoot', 'Django', 'Vue', 'React', 'MySQL'];

export default function BuildBasic({ index, Click }: buildProps) {
  const [naem, setName] = useState('');
  const [fileDir, setFileDir] = useState('');
  const [select, setSelect] = useState('');
  const [version, setVersion] = useState('');
  const [types, setTypes] = useState([]);
  const [versions, setVersions] = useState([]);

  const handleOnClick = () => {
    Click(index);
  };
  const handleChangeFrame = (event: string) => {
    setSelect(event);
  };

  return (
    <>
      <Grid container spacing={2}>
        <Grid item>
          <Typography>Name</Typography>
          <TextField
            id="outlined-basic"
            label="Name"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Name"
          />
        </Grid>
        <Grid item>
          <Typography>Framework/ Library</Typography>
          <SelectItem
            label="Framework/ Library"
            Items={FramAndLib}
            change={handleChangeFrame}
          />
        </Grid>
        <Grid item>
          <Typography>Version</Typography>
          <SelectItem label="versions" Items={versions} />
        </Grid>
        <Grid item>
          <Typography>Type</Typography>
          <SelectItem label="Types" Items={types} />
        </Grid>
        <Grid item />
        <Grid item>
          <Typography>File Dir</Typography>
          <TextField
            id="outlined-basic"
            label="Dir"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Dir"
          />
        </Grid>
      </Grid>
      <Stack direction="row" spacing={2}>
        <Button
          variant="outlined"
          startIcon={<ArrowDropDownIcon />}
          size="small"
        >
          Property
        </Button>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleOnClick}
        >
          Delete
        </Button>
      </Stack>
      <BuildProperty />
    </>
  );
}
