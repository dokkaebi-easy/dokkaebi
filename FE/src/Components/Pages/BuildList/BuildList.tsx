import MUIDataTable from 'mui-datatables';
import styled from '@emotion/styled';

const columns = [
  {
    name: 'name',
    label: '빌드번호',
    options: {
      filter: true,
      sort: true,
    },
  },
  {
    name: 'company',
    label: 'commit',
    options: {
      filter: true,
      sort: false,
    },
  },
  {
    name: 'city',
    label: 'git pull',
    options: {
      filter: true,
      sort: false,
    },
  },
  {
    name: 'state',
    label: 'Docker build',
    options: {
      filter: true,
      sort: false,
    },
  },
  {
    name: 'run',
    label: 'Docker run',
    options: {
      filter: true,
      sort: false,
    },
  },
];

const data = [
  {
    name: '# 1',
    company: '1',
    city: '완료',
    state: '완료',
    run: '완료',
  },
  {
    name: '# 2',
    company: '1',
    city: '완료',
    state: '완료',
    run: '완료',
  },
  { name: '# 3', company: '1', city: '완료', state: '완료', run: '완료' },
  {
    name: '# 4',
    company: '1',
    city: '완료',
    state: '완료',
    run: '실패',
  },
];

const options: any = {
  filterType: 'checkbox',
};

export default function BuildList() {
  return (
    <TableDiv>
      <MUIDataTable
        title="빌드목록"
        data={data}
        columns={columns}
        options={options}
      />
    </TableDiv>
  );
}

const TableDiv = styled.div`
  margin-top: 30px;
  margin-right: 20px;
  width: 600px;
`;
