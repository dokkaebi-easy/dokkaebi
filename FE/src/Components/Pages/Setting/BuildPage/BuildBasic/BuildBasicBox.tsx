import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';
import Paper from '@mui/material/Paper';
import BuildProperty from '../BuildProperty/BuildPropertyBox';

interface Version {
  version: string[];
}
interface Types {
  version: string[];
}

interface buildProps {
  index: number;
  value: Build;
  DelClick: (value: number) => void;
}

const FramAndLib = ['SpringBoot', 'Django', 'Vue', 'React', 'MySQL'];

export default function BuildBasicBox({ index, value, DelClick }: buildProps) {
  const [name, setName] = useState(value.name);
  const [fileDir, setFileDir] = useState(value.projectDirectory);
  const [select, setSelect] = useState('');
  const [version, setVersion] = useState('');
  const [type, setType] = useState('');
  const [types, setTypes] = useState([]);
  const [versions, setVersions] = useState([]);

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    value.name = event.target.value;
  };
  const handleFileDirOnClick = (event: any) => {
    setFileDir(event.target.value);
    value.projectDirectory = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsFLChange = (event: string) => {
    setSelect(event);
    setVersion('');
    setType('');
    value.frameworkName = event;
    value.version = '';
    value.type = '';
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
  };
  const handlePropsTypeChange = (event: string) => {
    setType(event);
  };

  return (
    <Box>
      <Paper sx={{ padding: 3 }}>
        <Grid container spacing={2}>
          <Grid item>
            <Typography>Name</Typography>
            <TextField
              defaultValue={name}
              id="outlined-basic"
              label="Name"
              variant="outlined"
              size="small"
              sx={{ my: 1 }}
              placeholder="Name"
              onChange={handleNameOnChange}
            />
          </Grid>
          <Grid item>
            <Typography>Framework/ Library</Typography>
            <SelectItem
              defaultValue={value.frameworkName}
              label="Framework/ Library"
              Items={FramAndLib}
              change={handlePropsFLChange}
            />
          </Grid>
          <Grid item>
            <Typography>Version</Typography>
            <SelectItem
              defaultValue={value.version}
              label="versions"
              Items={versions}
              change={handlePropsVersionChange}
            />
          </Grid>
          <Grid item>
            <Typography>Type</Typography>
            <SelectItem
              defaultValue={value.type}
              label="Types"
              Items={types}
              change={handlePropsTypeChange}
            />
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
              defaultValue={fileDir}
              onChange={handleFileDirOnClick}
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
            onClick={handleDelOnClick}
          >
            Delete
          </Button>
        </Stack>
        <BuildProperty buildValue={value} />
      </Paper>
    </Box>
  );
}
